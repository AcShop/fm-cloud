package me.fm.service.impl;

import java.util.List;

import me.fm.cloud.model.Mcat;
import me.fm.service.McatService;

import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.SqlBase;

@Service
public class McatServiceImpl implements McatService {

	@Override
	public Mcat get(Integer id) {
		SqlBase base = SqlBase.select("select * from t_mcat");
		base.eq("id", id);
		return Mcat.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public List<Mcat> getList(Integer status) {
		SqlBase base = SqlBase.select("select t.* from t_mcat t");
		base.eq("t.status", status);
		return Mcat.db.findList(base.getSQL(), base.getParams());
	}

	@Override
	public boolean save(String name) {
		if (StringUtils.isNotBlank(name)) {
			return Mcat.db.update("insert into t_mcat(name, status) values(?,?)", name, 1) > 0;
		}
		return false;
	}

	@Override
	public boolean update(Integer id, String name, Integer status) {
		SqlBase base = SqlBase.update("update t_mcat");
		base.set("name", name).set("status", status);
		base.eq("id", id);
		return Mcat.db.update(base.getSQL(), base.getParams()) > 0;
	}
}
