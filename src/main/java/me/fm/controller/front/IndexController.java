package me.fm.controller.front;

import java.io.IOException;
import java.util.Map;

import me.fm.cloud.model.Open;
import me.fm.cloud.model.User;
import me.fm.controller.BaseController;
import me.fm.service.MusicService;
import me.fm.service.OpenService;
import me.fm.service.SpecialService;
import me.fm.service.UserService;
import me.fm.util.SessionUtil;
import me.fm.util.WebConst;

import org.apache.log4j.Logger;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.dao.Page;
import org.unique.plugin.mail.SendMail;
import org.unique.web.annotation.Path;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * 前台首页
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
@Path("/")
public class IndexController extends BaseController {

	private Logger logger = Logger.getLogger(IndexController.class);
			
	@Autowired
	private UserService userService;
	@Autowired
	private MusicService musicService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private OpenService openService;

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
	 * 用qq登录
	 */
	public void qq_login() {
		try {
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (QQConnectException e) {
			logger.warn(e.getMessage());
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}

	/**
	 * 用qq登录回调
	 */
	public void qq_callback() {
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
				
				WebConst.QQ_TOKEN = accessToken;
				WebConst.QQ_TOKEN_EXPIREIN = tokenExpireIn;
				
				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				
				//去数据库查询是否绑定openid
				Open openUser = openService.get(null, openID, 1);
				if(null != openUser){
					User user = userService.get(openUser.getEmail(), 1);
					if(null != user){
						SessionUtil.setLoginUser(user);
						this.redirect("/admin/index");
					}
				} else{
					UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
					UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
					this.setAttr("openid", openID);
					this.setAttr("nickname", userInfoBean.getNickname());
					this.render("/bind_qq");
				}
			}
		} catch (QQConnectException e) {
			logger.warn(e.getMessage());
		}
	}
	
	/**
	 * 确认绑定qq
	 */
	public void save_bind_qq(){
		String openId = this.getPara("openid");
		String email = this.getPara("email");
		String nickname = this.getPara("nickname");
		String password = this.getPara("password");
		if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(email)){
			if(null == openService.get(null, openId, 1)){
				int count = openService.save(email, 1, openId);
				if(count > 0){
					User user = userService.get(email, 1);
					//登录管理员 放入session
					if (null != user) {
						SessionUtil.setLoginUser(user);
						this.redirect("/admin/index");
					} else {
						user = userService.register(nickname, email, password, StringUtils.getIP(request));
						if(null != user){
							SessionUtil.setLoginUser(user);
							this.redirect("/admin/index");
						} else{
							this.renderText(WebConst.MSG_FAILURE);
						}
					}
					return;
				} else{
					this.renderText(WebConst.MSG_FAILURE);
				}
			} else{
				this.renderText(WebConst.MSG_EXIST);
			}
			return;
		}
		this.renderText(WebConst.MSG_ERROR);
	}

	/**
	 * 音乐列表
	 */
	public void music() {
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		Page<Map<String, Object>> pageList = musicService.getPageMapList(uid, singer, song, null, null, 1, page, pageSize,
				"create_time desc");
		this.setAttr("pageData", pageList.getResults());
		this.render("music");
	}

	/**
	 * 电台列表
	 */
	public void radio() {
		//获取推荐的前9个
		Page<Map<String, Object>> specialPage = specialService.getPageMapList(null, null, 1, 1, 1, 9, "last_time desc");
		this.setAttr("pageData", specialPage.getResults());
		this.render("radio");
	}
	
	public void send() {
		String url = "http://fm.im90.me/active?code="+132123213131313L;
		// 发送邮件
		SendMail.asynSend("七牛云音乐电台激活帐号通知", 
				"点击链接激活您的邮箱  <a herf='"+url+"' target='_blank'>"+url+"</a>", "renqi@feitan.net");
	}
}
