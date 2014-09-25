package me.fm.controller.admin;

import java.util.List;
import java.util.Map;

import me.fm.cloud.model.Mcat;
import me.fm.cloud.model.User;
import me.fm.controller.BaseController;
import me.fm.service.McatService;
import me.fm.service.MusicService;
import me.fm.service.SettingService;
import me.fm.service.SpecialService;
import me.fm.service.UserService;
import me.fm.util.SessionUtil;
import me.fm.util.WebConst;

import org.apache.commons.lang3.StringUtils;
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
		Page<Map<String, Object>> musicPage = musicService.getPageMapList(uid, singer, song, null, null, 1, page,
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
	
	@Action("music/save")
	public void save_music() {
		Integer mid = this.getParaToInt("mid");
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		String song_path = this.getPara("song_path");
		String cover_path = this.getPara("cover_path");
		String introduce = this.getPara("introduce");
		String cids = this.getPara("cids");
		String step = this.getPara("step");
		if(StringUtils.isNoneBlank(step)){
			boolean flag = false;
			uid = 1;
			if(null != mid){
				flag = musicService.update(uid, singer, song, song_path, cover_path, introduce, cids, null, null) > 0;
			} else{
				flag = musicService.save(uid, singer, song, song_path, cover_path, introduce, cids, null, null);
			}
			if(flag){
				this.renderText(WebConst.MSG_SUCCESS);
			} else{
				this.renderText(WebConst.MSG_ERROR);
			}
			return;
		}
		this.render("edit_music");
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
	 * 编辑音乐
	 */
	@Action("music/{mid}")
	public void edit_music(){
		Integer mid = this.getParaToInt();
		// 编辑
		if(null != mid){
			Map<String, Object> music = musicService.getMap(null, mid);
			this.setAttr("music", music);
		}
		List<Mcat> mcatList = mcatService.getList(1);
		this.setAttr("catList", mcatList);
		this.render("edit_music");
	}
	
	/**
	 * 删除用户
	 */
	@Action("music/del")
	public void delete_music() {
		Integer mid = this.getParaToInt("mid");
		boolean flag = false;
		if(null != mid){
			flag = musicService.enable(mid, 0);
			if (flag) {
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_FAILURE);
			}
		} else{
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
