package org.unique.cloud.service.impl;

import java.util.List;

import org.unique.cloud.model.Mcat;
import org.unique.cloud.service.McatService;
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
}
