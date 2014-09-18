package org.unique.cloud.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.unique.cloud.api.QiniuApi;
import org.unique.cloud.model.Music;
import org.unique.cloud.service.FileService;
import org.unique.cloud.service.MusicService;
import org.unique.cloud.service.UserService;
import org.unique.cloud.util.AttachUtil;
import org.unique.cloud.util.BeanUtil;
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

	@Override
	public Music get(Integer mid) {
		SqlBase base = SqlBase.select("select * from t_music");
		base.eq("id", mid);
		return Music.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public boolean save(Integer uid, String singer, String song, String song_path, String cover_path, String introduce,
			String cids) {
		int count = 0;
		//1 上传到七牛
		String random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
		String key = AttachUtil.getMusicKey(uid, song_path, random);
		String cover_key = "";

		if (StringUtils.isNotBlank(song_path)) {
			if (FileUtil.exists(song_path)) {
				//上传歌曲
				int code = fileService.upload(key, song_path);
				if (code == 200) {
					//增加用户剩余空间
					Entry musicEntry = fileService.getInfo(key);
					userService.updateUseSize(uid, +musicEntry.getFsize());
				}
			}
			if (song_path.startsWith("http://")) {
				cover_key = song_path;
			}
		}

		if (StringUtils.isNotBlank(cover_path)) {
			if (FileUtil.exists(cover_path)) {
				cover_key = AttachUtil.getMusicCoverKey(uid, song_path, random);
				//上传封面
				int code = fileService.upload(cover_key, cover_path);
				if (code == 200) {
					//增加用户剩余空间
					Entry coverEntry = fileService.getInfo(key);
					userService.updateUseSize(uid, +coverEntry.getFsize());
				}
			}
			if (cover_path.startsWith("http://")) {
				cover_key = cover_path;
			}
		}
		//2 保存数据库
		try {
			count = Music.db.update(
					"insert into t_music(uid, singer, song, song_path, cover_path, introduce, cids, create_time) "
							+ "values(?, ?, ?, ?, ?, ?, ?, ?)", uid, singer, song, key, cover_key, introduce, cids,
					DateUtil.getCurrentTime());
		} catch (UpdateException e) {
			logger.warn("添加音乐失败：" + e.getMessage());
			count = 0;
		}
		return count > 0;

	}

	@Override
	public List<Music> getList(Integer uid, String singer, String song, String order) {
		SqlBase base = SqlBase.select("select t.* from t_music t");
		base.likeLeft("uid", uid).likeLeft("singer", singer).likeLeft("song", song).order(order);
		return Music.db.findList(base.getSQL(), base.getParams());
	}

	@Override
	public Page<Music> getPageList(Integer uid, String singer, String song, Integer page, Integer pageSize, String order) {
		SqlBase base = SqlBase.select("select t.* from t_music t");
		base.likeLeft("uid", uid).likeLeft("singer", singer).likeLeft("song", song).order(order);
		return Music.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public int delete(Integer id) {
		int count = 0;
		if (null != id) {
			Music music = this.get(id);
			if (null != music) {
				String key = music.getSong_path();
				if (StringUtils.isNotBlank(key)) {
					Entry musicEntry = fileService.getInfo(key);
					if (musicEntry.getStatusCode() == 200) {
						userService.updateUseSize(music.getUid(), -musicEntry.getFsize());
					}
				}
				String cover_key = music.getCover_path();
				if (StringUtils.isNotBlank(cover_key)) {
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
			String cids) {
		int count = 0;
		if (null != id) {

			Music music = this.get(id);
			if (null != music) {
				String random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
				String key = AttachUtil.getMusicKey(music.getUid(), song_path, random);
				String cover_key = "";

				SqlBase base = SqlBase.update("update t_music");
				base.set("singer", singer);

				//判断音乐文件是否修改
				if (StringUtils.isNotBlank(song_path) && FileUtil.exists(song_path)) {
					//删除原有文件
					String oldKey = music.getSong_path();
					fileService.delete(oldKey);

					fileService.upload(key, song_path);

					base.set("song_path", key);
				}
				//判断音乐封面是否修改
				if (StringUtils.isNotBlank(cover_path) && FileUtil.exists(cover_path)) {
					cover_key = AttachUtil.getMusicCoverKey(music.getUid(), song_path, random);
					//删除原有文件
					String oldKey = music.getCover_path();
					fileService.delete(oldKey);

					fileService.upload(cover_key, song_path);

					base.set("cover_path", cover_key);
				}
				base.set("introduce", introduce).set("cids", cids).eq("id", id);
				try {
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
	public List<Music> getRandom(Integer count) {
		return Music.db.findList("select * from t_music t1 " + "join(select max(id) id from  t_music) t2 "
				+ "on (t1.id >= floor( t2.id*rand() )) limit ?", count);
	}

	@Override
	public void like(Integer id, Integer type) {
		if (null != id && null != type) {
			switch (type) {
			case 1:
				Music.db.update("update t_music set like_count=(like_count + 1) where id = ?", id);
				break;
			case 2:
				Music.db.update("update t_music set listen_count=(listen_count + 1) where id = ?", id);
				break;
			case 3:
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
			if(StringUtils.isNotBlank(music.getCover_path())){
				if (music.getCover_path().startsWith("http://")) {
					resultMap.put("cover_url", music.getCover_path());
				} else {
					String cover_url = QiniuApi.getUrlByKey(music.getCover_path());
					resultMap.put("cover_url", cover_url);
				}
			}
			// 上传日期
			if(null != music.getCreate_time()){
				resultMap.put("time_zh", DateUtil.convertIntToDatePattern(music.getCreate_time(), "yyyy/MM/dd"));
			}
		}
		return resultMap;
	}

	@Override
	public Page<Map<String, Object>> getPageMapList(Integer uid, String singer, String song, Integer page,
			Integer pageSize, String order) {
		Page<Music> pageList = this.getPageList(uid, singer, song, page, pageSize, order);
		
		List<Music> musicList = pageList.getResults();
		Page<Map<String, Object>> pageMap = new Page<Map<String,Object>>((long) musicList.size(), pageSize, pageSize);
		
		List<Map<String, Object>> listMap = CollectionUtil.newArrayList();
		for (int i = 0, len = musicList.size(); i < len; i++) {
			Music music = musicList.get(i);
			if(null != music){
				listMap.add(this.getMap(music, null));
			}
		}
		pageMap.setResults(listMap);
		return pageMap;
	}

}
