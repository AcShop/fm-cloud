package org.unique.cloud.controller.admin;

import java.util.List;
import java.util.Map;

import org.unique.cloud.controller.BaseController;
import org.unique.cloud.model.Mcat;
import org.unique.cloud.model.User;
import org.unique.cloud.service.McatService;
import org.unique.cloud.service.MusicService;
import org.unique.cloud.service.SettingService;
import org.unique.cloud.service.SpecialService;
import org.unique.cloud.service.UserService;
import org.unique.cloud.util.SessionUtil;
import org.unique.cloud.util.WebConst;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.dao.Page;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Path;

/**
 * 用户后台
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/admin")
public class IndexController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private SettingService settingService;
	@Autowired
	private MusicService musicService;
	@Autowired
	private McatService mcatService;
	@Autowired
	private SpecialService specialService;

	/**
	 * 后台首页
	 */
	public void index() {
		this.render("index");
	}

	/**
	 * 音乐列表
	 */
	public void music() {
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		Page<Map<String, Object>> musicPage = musicService.getPageMapList(uid, singer, song, null, null, page,
				pageSize, "create_time");
		this.setAttr("pageMap", musicPage);
		this.render("music");
	}

	/**
	 * 用户列表
	 */
	public void users() {
		Page<Map<String, Object>> userPage = userService.getPageMapList(null, null, null, page, pageSize, "uid desc");
		this.setAttr("userPage", userPage);
		this.render("user");
	}

	/**
	 * 分类列表
	 */
	public void mcat() {
		List<Mcat> mcatList = mcatService.getList(null);
		this.setAttr("mcatList", mcatList);
		this.render("mcat");
	}

	/**
	 * 专辑列表
	 */
	public void special() {
		Page<Map<String, Object>> specialPage = specialService.getPageMapList(null, null, null, null, page, pageSize, "last_time desc");
		this.setAttr("specialPage", specialPage);
		this.render("special");
	}

	/**
	 * 删除用户
	 */
	@Action("users/del")
	public void delete_user() {
		Integer uid = this.getParaToInt("uid");
		int count = userService.update(uid, null, null, null, 0);
		if (count > 0) {
			this.renderText(WebConst.MSG_SUCCESS);
		} else {
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
	
	/**
	 * 保存用户
	 */
	@Action("users/save")
	public void save_user() {
		Integer uid = this.getParaToInt("uid");
		if(null != uid){
			String nickname = this.getPara("nickname");
			Long space_size = this.getParaToLong("space_size");
			int count = userService.update(uid, null, nickname, space_size, null);
			if(count > 0){
				this.renderText(WebConst.MSG_SUCCESS);
			} else{
				this.renderText(WebConst.MSG_FAILURE);
			}
			return;
		}
		this.render("edit_user");
	}
	
	/**
	 * 编辑用户
	 */
	@Action("users/{uid}")
	public void edit_user(){
		Integer uid = this.getParaToInt();
		// 编辑
		if(null != uid){
			Map<String, Object> user = userService.getMap(null, uid);
			this.setAttr("user", user);
		}
		this.render("edit_user");
	}
	
	/**
	 * 删除用户
	 */
	@Action("music/del")
	public void delete_music() {
		Integer mid = this.getParaToInt("mid");
		int count = musicService.delete(mid);
		if (count > 0) {
			this.renderText(WebConst.MSG_SUCCESS);
		} else {
			this.renderText(WebConst.MSG_FAILURE);
		}
	}

	/**
	 * 用户登录
	 */
	public void login() {
		String step = this.getPara("step");
		if (null != step && step.equals("login")) {
			String email = this.getPara("login_name");
			String pass_word = this.getPara("pass_word");
			User user = userService.login(email, pass_word);
			//登录成功
			if (null != user) {
				SessionUtil.setLoginUser(user);
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_FAILURE);
			}
			return;
		}
		this.render("login");
	}

}
