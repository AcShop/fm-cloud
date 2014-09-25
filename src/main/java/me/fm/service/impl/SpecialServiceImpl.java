package me.fm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import me.fm.api.QiniuApi;
import me.fm.cloud.model.Special;
import me.fm.service.FileService;
import me.fm.service.SpecialService;
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

@Service
public class SpecialServiceImpl implements SpecialService {

	private Logger logger = Logger.getLogger(SpecialServiceImpl.class);

	@Autowired
	private FileService fileService;

	@Override
	public Special get(Integer sid) {
		SqlBase base = SqlBase.select("select * from t_special");
		base.eq("id", sid);
		return Special.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public Map<String, Object> getMap(Special special, Integer sid) {
		Map<String, Object> resultMap = CollectionUtil.newHashMap();
		if (null == special) {
			special = this.get(sid);
		}
		if (null != special) {
			resultMap = BeanUtil.toMap(special);

			// 小图
			if (StringUtils.isNotBlank(special.getCover_small())) {
				if (special.getCover_small().startsWith("http://")) {
					resultMap.put("cover_small_url", special.getCover_small());
				} else {
					String cover_url = QiniuApi.getUrlByKey(special.getCover_small());
					resultMap.put("cover_small_url", cover_url);
				}
			}
			// 大图
			if (StringUtils.isNotBlank(special.getCover_pic())) {
				if (special.getCover_pic().startsWith("http://")) {
					resultMap.put("cover_pic_url", special.getCover_pic());
				} else {
					String cover_url = QiniuApi.getUrlByKey(special.getCover_pic());
					resultMap.put("cover_pic_url", cover_url);
				}
			}
			// 创建时间
			if (null != special.getCreate_time()) {
				resultMap.put("create_time_zh",
						DateUtil.convertIntToDatePattern(special.getCreate_time(), "yyyy-MM-dd HH:mm"));
			}
			// 最后更新时间
			if (null != special.getLast_time()) {
				resultMap.put("last_time_zh",
						DateUtil.convertIntToDatePattern(special.getLast_time(), "yyyy-MM-dd HH:mm"));
			}

		}
		return resultMap;
	}

	@Override
	public boolean save(Integer uid, String title, String introduce, String cover_small, String cover_pic,
			Integer is_top, Integer status) {
		int count = 0;
		Integer currentTime = DateUtil.getCurrentTime();

		//1 上传到七牛
		String random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
		String small_key = AttachUtil.getPicKey(uid, cover_small, random);
		random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
		String big_key = AttachUtil.getPicKey(uid, cover_pic, random);
		if (StringUtils.isNotBlank(cover_small) && FileUtil.exists(cover_small)) {
			if (cover_small.startsWith("http://")) {
				small_key = cover_small;
			} else {
				//上传小图
				fileService.upload(small_key, cover_small);
			}
		}
		if (StringUtils.isNotBlank(cover_pic) && FileUtil.exists(cover_pic)) {
			if (cover_pic.startsWith("http://")) {
				big_key = cover_pic;
			} else {
				//上传大图
				fileService.upload(big_key, cover_pic);
			}
		}
		try {
			count = Special.db.update(
					"insert into t_special(uid, title, introduce, cover_small, cover_pic, is_top, create_time, last_time, status) "
							+ "values(?,?,?,?,?,?,?,?,?)", uid, title, introduce, small_key, big_key, is_top,
					currentTime, currentTime, status);
		} catch (UpdateException e) {
			logger.warn("保存专辑失败：" + e.getMessage());
			count = 0;
		}
		return count > 0;
	}

	@Override
	public int update(Integer sid, Integer uid, String title, String introduce, String cover_small, String cover_pic,
			Integer is_top, Integer status) {
		int count = 0;
		if (null != sid) {
			Special special = this.get(sid);
			if (null != special) {
				SqlBase base = SqlBase.update("update t_special");

				if (StringUtils.isNotBlank(title) && !title.equals(special.getTitle())) {
					base.set("title", title);
				}

				if (StringUtils.isNotBlank(introduce) && !introduce.equals(special.getIntroduce())) {
					base.set("introduce", introduce);
				}
				// 缩略图是否修改
				if (StringUtils.isNotBlank(cover_small) && FileUtil.exists(cover_small)) {
					String small_key = "";
					if (StringUtils.isEmpty(special.getCover_small())
							|| (StringUtils.isNotBlank(special.getCover_small()) && !cover_small.endsWith(special
									.getCover_small()))) {
						String radom = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
						small_key = AttachUtil.getMusicCoverKey(special.getUid(), cover_small, radom);
						//删除原有文件
						String oldKey = special.getCover_small();
						fileService.delete(oldKey);

						fileService.upload(small_key, cover_small);
						base.set("cover_small", small_key);
					}
				}
				// 封面是否修改
				if (StringUtils.isNotBlank(cover_pic) && FileUtil.exists(cover_pic)) {
					String big_key = "";
					if (StringUtils.isEmpty(special.getCover_small())
							|| (StringUtils.isNotBlank(special.getCover_pic()) && !cover_pic.endsWith(special
									.getCover_pic()))) {
						String radom = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
						big_key = AttachUtil.getMusicCoverKey(special.getUid(), cover_pic, radom);
						//删除原有文件
						String oldKey = special.getCover_pic();
						fileService.delete(oldKey);

						fileService.upload(big_key, cover_pic);
						base.set("cover_pic", big_key);
					}
				}

				if (null != is_top && is_top.compareTo(special.getIs_top()) != 0) {
					base.set("is_top", is_top);
				}
				base.set("last_time", DateUtil.getCurrentTime());
				base.eq("id", sid);
				try {
					count = Special.db.update(base.getSQL(), base.getParams());
				} catch (UpdateException e) {
					logger.warn("更新专辑失败：" + e.getMessage());
					count = 0;
				}
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> getList(Integer uid, String title, Integer is_top, Integer status, String order) {
		SqlBase base = SqlBase.select("select t.* from t_special t");
		base.eq("uid", uid).likeLeft("title", title).eq("is_top", is_top).eq("status", status).order(order);
		List<Special> list = Special.db.findList(base.getSQL(), base.getParams());
		return this.getSpecialMapList(list);
	}

	private List<Map<String, Object>> getSpecialMapList(List<Special> list) {
		List<Map<String, Object>> mapList = CollectionUtil.newArrayList();
		for (int i = 0, len = list.size(); i < len; i++) {
			Special special = list.get(i);
			if (null != special) {
				mapList.add(this.getMap(special, null));
			}
		}
		return mapList;
	}

	@Override
	public Page<Special> getPageList(Integer uid, String title, Integer is_top, Integer status, Integer page,
			Integer pageSize, String order) {
		SqlBase base = SqlBase.select("select t.* from t_special t");
		base.eq("uid", uid).likeLeft("title", title).eq("is_top", is_top).eq("status", status).order(order);
		return Special.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public Page<Map<String, Object>> getPageMapList(Integer uid, String title, Integer is_top, Integer status,
			Integer page, Integer pageSize, String order) {
		Page<Special> pageList = this.getPageList(uid, title, is_top, status, page, pageSize, order);

		List<Special> specialList = pageList.getResults();
		Page<Map<String, Object>> pageMap = new Page<Map<String, Object>>((long) specialList.size(), pageSize, pageSize);

		List<Map<String, Object>> listMap = this.getSpecialMapList(specialList);
		pageMap.setResults(listMap);
		return pageMap;
	}

	@Override
	public int delete(Integer sid) {
		int count = 0;
		if (null != sid) {
			try {
				SqlBase base = SqlBase.update("update t_special");
				base.set("status", 0).eq("id", sid);
				count = Special.db.update(base.getSQL(), base.getParams());
				//Special.db.delete("delete from t_special where id = ?", sid);
			} catch (UpdateException e) {
				logger.warn("删除专辑失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int hit(Integer sid) {
		if (null != sid) {
			return Special.db.update("update t_special hit = (hit+1) where id = ?", sid);
		}
		return 0;
	}

	@Override
	public boolean enable(Integer sid, Integer status) {
		if (null != sid) {
			try {
				SqlBase base = SqlBase.update("update t_special");
				base.set("status", status).eq("id", sid);
				return Special.db.update(base.getSQL(), base.getParams()) > 0;
			} catch (UpdateException e) {
				logger.warn("停用启用专辑失败：" + e.getMessage());
				return false;
			}
		}
		return false;
	}

}
