package me.fm.service.impl;

import java.util.List;
import java.util.Map;

import me.fm.cloud.model.Special;
import me.fm.service.SpecialService;
import me.fm.util.BeanUtil;

import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.DateUtil;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class SpecialServiceImpl implements SpecialService {

	private Logger logger = Logger.getLogger(SpecialServiceImpl.class);

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
		}
		return resultMap;
	}

	@Override
	public boolean save(Integer uid, String title, String introduce, String cover_small, String cover_pic,
			Integer is_top, Integer status) {
		int count = 0;
		Integer currentTime = DateUtil.getCurrentTime();
		try {
			count = Special.db.update(
					"insert into t_special(uid, title, introduce, cover_small, cover_pic, is_top, create_time, last_time, status) "
							+ "values(?,?,?,?,?,?,?,?,?)", uid, title, introduce, cover_small, cover_pic, is_top,
					currentTime, currentTime);
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
			SqlBase base = SqlBase.update("update t_special");
			base.set("uid", uid).set("title", title).set("introduce", introduce).set("cover_small", cover_small)
					.set("cover_pic", cover_pic).set("is_top", is_top).eq("sid", sid);
			try {
				count = Special.db.update(base.getSQL(), base.getParams());
			} catch (UpdateException e) {
				logger.warn("更新专辑失败：" + e.getMessage());
				count = 0;
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
				Special.db.delete("delete from t_special where id = ?", sid);
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

}
