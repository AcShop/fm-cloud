package org.unique.cloud.service.impl;

import java.util.List;

import org.unique.cloud.model.Open;
import org.unique.cloud.model.User;
import org.unique.cloud.service.ActiveService;
import org.unique.cloud.service.OpenService;
import org.unique.cloud.service.UserService;
import org.unique.cloud.util.Base64;
import org.unique.cloud.util.EncrypHandler;
import org.unique.common.tools.DateUtil;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private OpenService openService;
	@Autowired
	private ActiveService activeService;

	private User find(Integer uid, String email, Integer status) {
		SqlBase base = SqlBase.select("select * from t_user u");
		base.eq("u.uid", uid).eq("u.email", email).eq("u.status", status);
		return User.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public User getByUid(Integer uid) {
		return this.find(uid, null, null);
	}

	@Override
	public User register(String nickname, String email, String password, String ip) {
		User user = null;
		//密码规则:md5(email+password)
		String md5pwd = EncrypHandler.md5(email + password);
		Integer currtime = DateUtil.getCurrentTime();
		int count = 0;
		try {
			count = User.db.update("insert into t_user(nickname, email, password, reg_ip, reg_time, log_time, status) "
					+ "values(?, ?, ?, ?, ?, ?, ?)", nickname, email, md5pwd, ip, currtime, currtime, 0);
		} catch (UpdateException e) {
			count = 0;
		}
		if (count > 0) {
			user = this.find(null, email, 1);
			// 生成激活码: sha1(email) 激活码
			String code = Base64.encoder(email);
			activeService.save(user.getUid(), code);
			// 发送邮件
		}
		return user;
	}

	@Override
	public boolean exists(String email) {
		return null == this.find(null, email, 1);
	}

	@Override
	public List<User> getList(String nickname, String email, Integer status, String order) {
		SqlBase base = SqlBase.select("select u.* from t_user u");
		base.likeLeft("u.nickname", nickname).likeLeft("u.email", email).eq("u.status", status).order("u." + order);
		return User.db.findList(base.getSQL(), base.getParams());
	}

	@Override
	public Page<User> getPageList(String nickname, String email, Integer status, Integer page, Integer pageSize,
			String order) {
		SqlBase base = SqlBase.select("select u.* from t_user u");
		base.likeLeft("u.nickname", nickname).likeLeft("u.email", email).eq("u.status", status).order("u." + order);
		return User.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public int delete(String email, Integer uid) {
		int count = 0;
		if (null != uid) {
			try {
				count = User.db.delete("delete from t_user u where u.uid = ?", uid);
			} catch (UpdateException e) {
				count = 0;
			}
		}
		if (StringUtils.isNotBlank(email)) {
			try {
				count = User.db.delete("delete from t_user u where u.email = ?", email);
			} catch (UpdateException e) {
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int deleteBatch(String uids) {
		int count = 0;
		if (null != uids) {
			try {
				count = User.db.delete("delete from t_user u where u.uid in (?)", uids);
			} catch (UpdateException e) {
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int enable(String email, Integer uid, Integer status) {
		if (null != uid) {
			return User.db.update("update t_user u set u.status = ? where u.uid = ?", status, uid);
		}
		if (StringUtils.isNotBlank(email)) {
			return User.db.update("update t_user u set u.status = ? where u.email = ?", status, email);
		}
		return 0;
	}

	@Override
	public User login(String email, String password) {
		String pwd = EncrypHandler.md5(email + password);
		User user = this.find(null, email, 1);
		if (null != user && user.getPassword().equals(pwd)) {
			return user;
		}
		return null;
	}

	@Override
	public int updateUseSize(Integer uid, Long useSpace) {
		int count = 0;
		if (null != uid && null != useSpace) {
			try {
				count = User.db.update("update t_user u set u.use_size = (u.use_size + ?) where u.uid = ?", useSpace,
						uid);
			} catch (UpdateException e) {
				count = 0;
			}
		}
		return count;
	}

	@Override
	public User openLogin(String openid, Integer type) {
		User user = null;
		Open open = openService.get(null, openid, type);
		if (null != open) {
			user = this.find(null, open.getEmail(), 1);
		}
		return user;
	}

	@Override
	public User openBind(Integer type, String openid, String nickName, String email, String ip) {
		String pwd = StringUtils.randomStr(6);
		User user = this.register(nickName, email, pwd, ip);
		openService.save(email, type, openid);
		return user;
	}

	@Override
	public User get(String email, Integer status) {
		return this.find(null, email, status);
	}

	@Override
	public int updateStatus(Integer uid, String email, Integer status) {
		int count = 0;
		SqlBase base = SqlBase.update("update t_user u");
		base.set("u.status", status).eq("u.uid", uid).eq("u.email", email);
		try {
			count = User.db.update(base.getSQL(), base.getParams());
		} catch (UpdateException e) {
			count = 0;
		}
		return count;
	}

}
