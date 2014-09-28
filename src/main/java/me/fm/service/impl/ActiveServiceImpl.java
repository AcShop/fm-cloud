package me.fm.service.impl;

import java.io.UnsupportedEncodingException;

import me.fm.cloud.model.Active;
import me.fm.service.ActiveService;
import me.fm.service.UserService;
import me.fm.util.Base64;

import org.apache.log4j.Logger;
import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class ActiveServiceImpl implements ActiveService {

	private Logger logger = Logger.getLogger(ActiveServiceImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public int save(Integer uid, String code) {
		int count = 0;
		try {
			count = Active.db.update("insert into t_active(uid, code, status) values(?,?,?)", uid, code, 1);
		} catch (UpdateException e) {
			logger.warn("添加激活码失败：" + e.getMessage());
			count = 0;
		}
		return count;
	}

	@Override
	public Active get(Integer uid, String code) {
		SqlBase base = SqlBase.select("select * from t_active t");
        base.eq("t.uid", uid).eq("t.code", code);
		return Active.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public void active(String code) {
		try {
			String email = Base64.decoder(code);
			userService.update(null, email, null, null, 1);
		} catch (UnsupportedEncodingException e) {
			logger.warn("激活激活码失败：" + e.getMessage());
		}
	}

}
