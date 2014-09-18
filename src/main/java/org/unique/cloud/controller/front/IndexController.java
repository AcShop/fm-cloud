package org.unique.cloud.controller.front;

import java.io.IOException;

import org.unique.cloud.controller.BaseController;
import org.unique.cloud.model.User;
import org.unique.cloud.service.UserService;
import org.unique.cloud.util.SessionUtil;
import org.unique.cloud.util.WebConst;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Autowired;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Path;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

/**
 * 前台首页
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
@Path("/")
public class IndexController extends BaseController {

	@Autowired
	private UserService userService;
	
	public void index() {
		this.render("index");
	}

	/**
	 * 注册用户
	 */
	public void reg() {
		String username = this.getPara("username");
		String email = this.getPara("email");
		String password = this.getPara("password");
		User user = userService.register(username, email, password, StringUtils.getIP(this.getRequest()));
		if (null != user) {
			//成功
		}
	}

	/**
	 * 用户登录
	 */
	public void login() {
		String step = this.getPara("step");
		if (null != step && step.equals("login")) {
			String email = this.getPara("email");
			String pass_word = this.getPara("pass_word");
			User user = userService.login(email, pass_word);
			//登录成功
			if (null != user) {
				SessionUtil.setLoginUser(user);
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_FAILURE);
			}
		}
		this.render("login");
	}

	/**
	 * 管理员退出
	 */
	public void log_out() {
		SessionUtil.removeLoginUser();
		this.render("/");
	}

	/**
	 * 用qq登录
	 */
	public void qq_login() {
		String step = this.getPara("step");
		if(null != step && step.equals("login")){
//			String openid = this.getSessionAttr("unique_open_id");
//			
//			userService.qqLogin(openid, "", StringUtils.getIP(request));
		} else{
			try {
				response.sendRedirect(new Oauth().getAuthorizeURL(request));
			} catch (QQConnectException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 用qq登录回调
	 */
	public void qq_login_call_back() {
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权做一些数据统计工作
				System.out.print("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				
				this.setSessionAttr("unique_access_token", accessToken);
				this.setSessionAttr("unique_token_expirein", String.valueOf(tokenExpireIn));
                
				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				
				this.setSessionAttr("unique_open_id", openID);

				System.out.println("openid:" + openID);
				
//				User user = userService.qqLogin(openID, "test_qq_login", StringUtils.getIP(request));
//				SessionUtil.setFrontUser(user);
				this.render("/index");
			}
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}
	
	@Action("wx_call_back")
	public void upload(){
		
	}
	
}
