package me.fm.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.fm.api.QiniuApi;
import me.fm.cloud.model.Mcat;
import me.fm.cloud.model.Music;
import me.fm.cloud.model.User;
import me.fm.service.FileService;
import me.fm.service.McatService;
import me.fm.service.MusicService;
import me.fm.service.UserService;
import me.fm.util.AttachUtil;
import me.fm.util.BeanUtil;

import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.DateUtil;
import org.unique.common.tools.FileUtil;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

import com.qiniu.api.rs.Entry;

@Service
public class MusicServiceImpl implements MusicService {

	private Logger logger = Logger.getLogger(MusicServiceImpl.class);

	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	@Autowired
	private McatService mcatService;

	@Override
	public Music get(Integer mid) {
		SqlBase base = SqlBase.select("select * from t_music");
		base.eq("id", mid);
		return Music.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public boolean save(Integer uid, String singer, String song, String song_path, String cover_path, String introduce,
			String cids, String lrc, String tags, Integer sid) {
		int count = 0;
		//1 上传到七牛
		String random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
		String song_key = "", cover_key = "";

		if (StringUtils.isNotBlank(song_path)) {
			if (FileUtil.exists(song_path)) {
				song_key = AttachUtil.getMusicKey(uid, song_path, random);
				//上传歌曲
				fileService.upload(song_key, song_path);
				//增加用户剩余空间
				//				Entry musicEntry = fileService.getInfo(song_key);
				//				userService.updateUseSize(uid, +musicEntry.getFsize());
			}
			if (song_path.startsWith("http://")) {
				song_key = song_path;
			}
		}

		if (StringUtils.isNotBlank(cover_path)) {
			if (FileUtil.exists(cover_path)) {
				cover_key = AttachUtil.getMusicCoverKey(uid, cover_path, random);
				//上传封面
				fileService.upload(cover_key, cover_path);
				//增加用户剩余空间
				//				Entry coverEntry = fileService.getInfo(cover_key);
				//				userService.updateUseSize(uid, + coverEntry.getFsize());
			}
			if (cover_path.startsWith("http://")) {
				cover_key = cover_path;
			}
		}
		//2 保存数据库
		try {
			count = Music.db.update(
					"insert into t_music(uid, singer, song, song_path, cover_path, introduce, cids, lrc, tags, sid, create_time) "
							+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", uid, singer, song, song_key, cover_key,
					introduce, cids, lrc, tags, sid, DateUtil.getCurrentTime());
		} catch (UpdateException e) {
			logger.warn("添加音乐失败：" + e.getMessage());
			count = 0;
		}
		return count > 0;

	}

	@Override
	public List<Map<String, Object>> getList(Integer uid, String singer, String song, String tag, Integer sid,
			String order) {
		SqlBase base = SqlBase.select("select t.* from t_music t");
		base.eq("uid", uid).likeLeft("singer", singer).likeLeft("song", song).eq("sid", sid).like("tags", tag)
				.order(order);
		List<Music> list = Music.db.findList(base.getSQL(), base.getParams());
		return this.getMusicMapList(list);
	}

	@Override
	public Page<Music> getPageList(Integer uid, String singer, String song, String tag, Integer sid, Integer status,
			Integer page, Integer pageSize, String order) {
		SqlBase base = SqlBase.select("select t.* from t_music t");
		base.eq("t.uid", uid).likeLeft("t.singer", singer).like("t.song", song).eq("t.status", status).eq("t.sid", sid)
				.like("t.tags", tag).order(order);
		return Music.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public int delete(Integer id) {
		int count = 0;
		if (null != id) {
			Music music = this.get(id);
			if (null != music) {
				String key = music.getSong_path();
				if (StringUtils.isNotBlank(key) && !key.startsWith("http://")) {
					Entry musicEntry = fileService.getInfo(key);
					if (musicEntry.getStatusCode() == 200) {
						userService.updateUseSize(music.getUid(), -musicEntry.getFsize());
					}
				}
				String cover_key = music.getCover_path();
				if (StringUtils.isNotBlank(cover_key) && !cover_key.startsWith("http://")) {
					Entry coverEntry = fileService.getInfo(cover_key);
					if (coverEntry.getStatusCode() == 200) {
						userService.updateUseSize(music.getUid(), -coverEntry.getFsize());
					}
				}
				try {
					count = Music.db.delete("delete from t_music where id = ?", id);
				} catch (UpdateException e) {
					logger.warn("删除音乐失败：" + e.getMessage());
					count = 0;
				}
			}
		}
		return count;
	}

	@Override
	public int delete(String ids) {
		int count = 0;
		if (null != ids) {
			try {
				Music.db.delete("delete from t_music where id in (?)", ids);
			} catch (UpdateException e) {
				logger.warn("删除音乐失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int update(Integer id, String singer, String song, String song_path, String cover_path, String introduce,
			String cids, String lrc, String tags, Integer sid) {
		int count = 0;
		if (null != id) {

			Music music = this.get(id);
			if (null != music) {
				String random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
				String key = "", cover_key = "";

				SqlBase base = SqlBase.update("update t_music");
				//判断是否修改歌手
				if (StringUtils.isNotBlank(singer) && !singer.equals(music.getSinger())) {
					base.set("singer", singer);
				}
				//判断音乐文件是否修改
				if (StringUtils.isNotBlank(song_path)) {
					if (FileUtil.exists(song_path)) {
						if (!song_path.endsWith(music.getSong_path())) {
							key = AttachUtil.getMusicKey(music.getUid(), song_path, random);
							//删除原有文件
							String oldKey = music.getSong_path();
							fileService.delete(oldKey);

							fileService.upload(key, song_path);

							base.set("song_path", key);
						}
					} else if (song_path.startsWith("http://")) {
						base.set("song_path", song_path);
					}

				}
				//判断音乐封面是否修改
				if (StringUtils.isNotBlank(cover_path)) {
					if (FileUtil.exists(cover_path)) {
						if (!cover_path.endsWith(music.getCover_path())) {
							cover_key = AttachUtil.getMusicCoverKey(music.getUid(), cover_path, random);
							//删除原有文件
							String oldKey = music.getCover_path();
							fileService.delete(oldKey);

							fileService.upload(cover_key, cover_path);

							base.set("cover_path", cover_key);
						}
					} else if (cover_path.startsWith("http://")) {
						base.set("cover_path", cover_path);
					}
				}
				//是否修改描述
				if (StringUtils.isNotBlank(introduce) && !introduce.equals(music.getIntroduce())) {
					base.set("introduce", introduce);
				}
				//是否修改分类
				if (StringUtils.isNotBlank(cids) && !cids.equals(music.getCids())) {
					base.set("cids", cids);
				}
				//是否修改tags
				if (StringUtils.isNotBlank(tags) && !tags.equals(music.getTags())) {
					base.set("tags", tags);
				}
				//是否修改歌词信息
				if (StringUtils.isNotBlank(lrc) && !lrc.equals(music.getLrc())) {
					base.set("lrc", lrc);
				}
				base.eq("id", music.getId());
				try {
					if (base.getSetMap().size() == 0) {
						return 1;
					}
					count = Music.db.update(base.getSQL(), base.getParams());
				} catch (UpdateException e) {
					logger.warn("更新音乐失败：" + e.getMessage());
					count = 0;
				}
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> getRandom(Integer count) {
		List<Music> list = Music.db.findList(
				"select t1.id,t1.singer,t1.song,t1.song_path,t1.like_count from t_music t1 "
						+ "join(select max(id) id from  t_music) t2 " + "on (t1.id >= floor( t2.id*rand() )) limit ?",
				count);
		return this.getMusicMapList(list);
	}

	/**
	 * 私有的list转map
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> getMusicMapList(List<Music> list) {
		List<Map<String, Object>> mapList = CollectionUtil.newArrayList();
		for (int i = 0, len = list.size(); i < len; i++) {
			Music music = list.get(i);
			if (null != music) {
				mapList.add(this.getMap(music, null));
			}
		}
		return mapList;
	}

	@Override
	public void like(Integer id, Integer type) {
		if (null != id && null != type) {
			switch (type) {
			case 1:
				Music.db.update("update t_music set like_count=(like_count + 1) where id = ?", id);
				break;
			case 2:
				Music.db.update("update t_music set download_count=(download_count + 1) where id = ?", id);
				break;
			}
		}
	}

	@Override
	public Map<String, Object> getMap(Music music, Integer mid) {
		Map<String, Object> resultMap = CollectionUtil.newHashMap();
		if (null == music) {
			music = this.get(mid);
		}
		if (null != music) {
			resultMap = BeanUtil.toMap(music);
			// 歌曲路径
			if (StringUtils.isNotBlank(music.getSong_path())) {
				if (music.getSong_path().startsWith("http://")) {
					resultMap.put("mp3_url", music.getSong_path());
				} else {
					String music_url = QiniuApi.getUrlByKey(music.getSong_path());
					resultMap.put("mp3_url", music_url);
				}
			}
			// 歌曲封面
			if (StringUtils.isNotBlank(music.getCover_path())) {
				if (music.getCover_path().startsWith("http://")) {
					resultMap.put("cover_url", music.getCover_path());
				} else {
					String cover_url = QiniuApi.getUrlByKey(music.getCover_path());
					resultMap.put("cover_url", cover_url);
				}
			}
			// 所属分类
			if (StringUtils.isNotBlank(music.getCids())) {
				String[] cids = music.getCids().split(",");
				List<String> mcatList = CollectionUtil.newArrayList();
				if (null != cids && cids.length > 0) {
					for (String cid : cids) {
						Mcat mcat = mcatService.get(Integer.valueOf(cid));
						if (null != mcat) {
							mcatList.add(mcat.getName());
						}
					}
				}
				resultMap.put("mcat", mcatList.toString());
				resultMap.put("mcatList", Arrays.asList(cids));
			}
			// 上传日期
			if (null != music.getCreate_time()) {
				resultMap.put("date_zh", DateUtil.convertIntToDatePattern(music.getCreate_time(), "yyyy/MM/dd"));
				resultMap.put("time_zh",
						DateUtil.convertIntToDatePattern(music.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
			}
			// 上传人
			if (null != music.getUid()) {
				User user = userService.getByUid(music.getUid());
				resultMap.put("nickname", user.getNickname());
			}
		}
		return resultMap;
	}

	@Override
	public Page<Map<String, Object>> getPageMapList(Integer uid, String singer, String song, String tag, Integer sid,
			Integer status, Integer page, Integer pageSize, String order) {
		Page<Music> pageList = this.getPageList(uid, singer, song, tag, sid, status, page, pageSize, order);

		List<Music> musicList = pageList.getResults();
		Page<Map<String, Object>> pageMap = new Page<Map<String, Object>>((long) musicList.size(), pageSize, pageSize);

		List<Map<String, Object>> listMap = this.getMusicMapList(musicList);
		pageMap.setResults(listMap);
		return pageMap;
	}

	@Override
	public boolean enable(Integer mid, Integer status) {
		if (null != mid) {
			return Music.db.update("update t_music set status = ? where id = ?", status, mid) > 0;
		}
		return false;
	}

}
