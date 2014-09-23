package org.unique.cloud.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.unique.cloud.api.QiniuApi;
import org.unique.cloud.model.Music;
import org.unique.cloud.model.Special;
import org.unique.cloud.service.SpecialService;
import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.DateUtil;
import org.unique.common.tools.StringUtils;
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
			//封面图
			if (StringUtils.isNotBlank(special.getCover_pic())) {
				if (special.getCover_pic().startsWith("http://")) {
					resultMap.put("mp3_url", special.getCover_pic());
				} else {
					String music_url = QiniuApi.getUrlByKey(special.getCover_pic());
					resultMap.put("cover_url", music_url);
				}
			}
		}
		return resultMap;
	}

	@Override
	public boolean save(Integer uid, String title, String cover_pic) {
		int count = 0;
		try {
			count = Music.db.update("insert into t_special(uid, title, cover_pic, create_time)  "
					+ "values(?, ?, ?, ?)", uid, title, cover_pic, DateUtil.getCurrentTime());
		} catch (UpdateException e) {
			logger.warn("添加专辑失败：" + e.getMessage());
			count = 0;
		}
		return count > 0;
	}

	@Override
	public int update(Integer id, String title, Integer hit, String cover_pic) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> getList(Integer uid, String title, String order) {
		SqlBase base = SqlBase.select("select t.* from t_special t");
		base.eq("uid", uid).likeLeft("title", title).order(order);
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
	public Page<Special> getPageList(Integer uid, String title, Integer page, Integer pageSize, String order) {
		SqlBase base = SqlBase.select("select t.* from t_special t");
		base.eq("uid", uid).likeLeft("title", title).order(order);
		return Special.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public Page<Map<String, Object>> getPageMapList(Integer uid, String title, Integer page, Integer pageSize,
			String order) {
		Page<Special> pageList = this.getPageList(uid, title, page, pageSize, order);

		List<Special> specialList = pageList.getResults();
		Page<Map<String, Object>> pageMap = new Page<Map<String, Object>>((long) specialList.size(), pageSize, pageSize);

		List<Map<String, Object>> listMap = this.getSpecialMapList(specialList);
		pageMap.setResults(listMap);
		return pageMap;
	}

	@Override
	public int delete(Integer id) {
		int count = 0;
		if (null != id) {
			try {
				Special.db.delete("delete from t_special where id=?", id);
			} catch (UpdateException e) {
				logger.warn("删除专辑失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

}
