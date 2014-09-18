package org.unique.cloud.service.impl;

import org.unique.cloud.model.Setting;
import org.unique.cloud.model.User;
import org.unique.cloud.service.SettingService;
import org.unique.cloud.util.EncrypHandler;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Override
	public Setting get(String key) {
		SqlBase base = SqlBase.select("select * from t_setting t");
        base.eq("t.key", key);
        return Setting.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public int save(String key, String value) {
		int count = 0;
		try {
			count = User.db.update("insert into t_setting(key, value) values(?, ?)", key, value);
		} catch (UpdateException e) {
			e.printStackTrace();
			count = 0;
		}
		return count;
	}

	@Override
	public int update(String key, String value) {
		int count = 0;
		if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
			try {
				count = User.db.update("update t_setting t set t.value = ? where t.key = ?", value, key);
			} catch (UpdateException e) {
				e.printStackTrace();
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int delete(String key) {
		int count = 0;
		if(StringUtils.isNotBlank(key)){
			try {
				count = User.db.delete("delete from t_setting t where t.key = ?", key);
			} catch (UpdateException e) {
				e.printStackTrace();
				count = 0;
			}
		}
		return count;
	}

	@Override
	public String adminLogin(String login_name, String pass_word) {
	 	Setting setAdmin = this.get("admin_name");
	 	if(null != setAdmin && setAdmin.getValue().equals(login_name)){
	 		//密码规则: md5(loginname+pass_word)
	 		String pwd = EncrypHandler.md5(login_name + pass_word);
	 		Setting pass = this.get("admin_pass");
	 		if(pass.getValue().equals(pwd)){
	 			return EncrypHandler.md5(login_name);
	 		}
	 	}
		return null;
	}

}
