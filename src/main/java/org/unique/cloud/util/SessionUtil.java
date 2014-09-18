package org.unique.cloud.util;

import org.unique.cloud.model.User;
import org.unique.web.core.ActionContext;

/**
 * 设置session
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
public class SessionUtil {

	/**
	 * 设置登录用户
	 * @param user
	 */
	public static void setLoginUser(User user) {
		ActionContext.single().getHttpSession().setAttribute(WebConst.LOGIN_USER_SESSION_KEY, user);
	}

	/**
	 * 获取登录用户
	 * @return
	 */
	public static User getLoginUser() {
		Object user = ActionContext.single().getHttpSession().getAttribute(WebConst.LOGIN_USER_SESSION_KEY);
		if (null != user) {
			return (User) user;
		}
		return null;
	}

	public static void removeLoginUser() {
		ActionContext.single().getHttpSession().removeAttribute(WebConst.LOGIN_USER_SESSION_KEY);
	}
	
}
