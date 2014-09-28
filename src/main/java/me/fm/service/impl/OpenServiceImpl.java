package me.fm.service.impl;

import me.fm.cloud.model.Open;
import me.fm.cloud.model.User;
import me.fm.service.OpenService;

import org.apache.log4j.Logger;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class OpenServiceImpl implements OpenService {
	
	private Logger logger = Logger.getLogger(OpenServiceImpl.class);
	
	private Open find(Integer id, String email, String openid,
			Integer type, Integer status){
		SqlBase base = SqlBase.select("select * from t_open t");
        base.eq("t.id", id).eq("t.email", email).eq("t.openid", openid).eq("t.type", type).eq("t.status", status);
        return Open.db.find(base.getSQL(), base.getParams());
	}
	
	@Override
	public Open get(String email, String openid, Integer type) {
		return this.find(null, email, openid, type, 1);
	}

	@Override
	public int save(String email, Integer type, String openid) {
		int count = 0;
		try {
			count = User.db.update("insert into t_open(type, email, openid, status) "
					+ "values(?, ?, ?, ?)", type, email, openid, 1);
		} catch (UpdateException e) {
			logger.warn("保存open用户失败：" + e.getMessage());
			count = 0;
		}
		return count;
	}

}
