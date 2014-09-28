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
	 * 管理员退出
	 */
	public void logout(){
		SessionUtil.removeLoginUser();
		this.redirect("/");
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

	/*-----------------------------------music--------------------------------------------*/

	public void music() {
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		Page<Map<String, Object>> musicPage = musicService.getPageMapList(uid, singer, song, null, null, 1, page,
				pageSize, "id desc");
		this.setAttr("pageMap", musicPage);
		this.render("music");
	}

	/**
	 * 编辑音乐
	 */
	@Action("music/{mid}")
	public void edit_music() {
		Integer mid = this.getParaToInt();
		// 编辑
		if (null != mid) {
			Map<String, Object> music = musicService.getMap(null, mid);
			this.setAttr("music", music);
		}
		List<Mcat> mcatList = mcatService.getList(1);
		List<Map<String, Object>> specialList = specialService.getList(null, null, 1, 1, "id desc");
		this.setAttr("catList", mcatList);
		this.setAttr("specialList", specialList);
		this.render("edit_music");
	}

	/**
	 * 保存音乐
	 */
	@Action("music/save")
	public void save_music() {
		String step = this.getPara("step");
		if (StringUtils.isNoneBlank(step)) {
			Integer mid = this.getParaToInt("mid");
			String singer = this.getPara("singer");
			String song = this.getPara("song");
			Integer sid = this.getParaToInt("sid");
			String song_path = this.getPara("song_path");
			String cover_path = this.getPara("cover_path");
			String introduce = this.getPara("introduce");
			String lrc = this.getPara("lrc");
			String cids = this.getPara("cids");
			
			boolean flag = false;
			uid = 1;
			if (null != mid) {
				flag = musicService.update(mid, singer, song, song_path, cover_path, introduce, cids, lrc, null, sid) > 0;
			} else {
				flag = musicService.save(uid, singer, song, song_path, cover_path, introduce, cids, lrc, null, sid);
			}
			if (flag) {
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_ERROR);
			}
			return;
		}
		List<Mcat> mcatList = mcatService.getList(1);
		List<Map<String, Object>> specialList = specialService.getList(null, null, 1, 1, "id desc");
		this.setAttr("catList", mcatList);
		this.setAttr("specialList", specialList);
		this.render("edit_music");
	}

	/*-----------------------------------user--------------------------------------------*/

	/**
	 * 用户列表
	 */
	public void users() {
		Page<Map<String, Object>> userPage = userService.getPageMapList(null, null, null, page, pageSize, "uid desc");
		this.setAttr("userPage", userPage);
		this.render("user");
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
		if (null != uid) {
			String nickname = this.getPara("nickname");
			Long space_size = this.getParaToLong("space_size");
			int count = userService.update(uid, null, nickname, space_size, null);
			if (count > 0) {
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
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
	public void edit_user() {
		Integer uid = this.getParaToInt();
		// 编辑
		if (null != uid) {
			Map<String, Object> user = userService.getMap(null, uid);
			this.setAttr("user", user);
		}
		this.render("edit_user");
	}

	/**
	 * 删除音乐
	 */
	@Action("music/del")
	public void delete_music() {
		Integer mid = this.getParaToInt("mid");
		boolean flag = false;
		if (null != mid) {
			flag = musicService.enable(mid, 0);
			if (flag) {
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_FAILURE);
			}
		} else {
			this.renderText(WebConst.MSG_FAILURE);
		}

	}

	/*-----------------------------------mcat--------------------------------------------*/
	/**
	 * 分类列表
	 */
	public void mcat() {
		List<Mcat> mcatList = mcatService.getList(null);
		this.setAttr("mcatList", mcatList);
		this.render("mcat");
	}

	/**
	 * 保存分类
	 */
	@Action("mcat/save")
	public void save_cat() {
		Integer id = this.getParaToInt("id");
		String name = this.getPara("name");
		boolean flag = false;
		if (null != id) {
			flag = mcatService.update(id, name, null);
		} else {
			flag = mcatService.save(name);
		}
		if (flag) {
			this.renderText(WebConst.MSG_SUCCESS);
		} else {
			this.renderText(WebConst.MSG_FAILURE);
		}
	}

	/*-----------------------------------special--------------------------------------------*/
	/**
	 * 专辑列表
	 */
	public void special() {
		Page<Map<String, Object>> specialPage = specialService.getPageMapList(null, null, null, null, page, pageSize,
				"last_time desc");
		this.setAttr("specialPage", specialPage);
		this.render("special");
	}

	/**
	 * 保存专辑
	 */
	@Action("special/save")
	public void save_special() {
		String step = this.getPara("step");
		if (StringUtils.isNoneBlank(step)) {
			Integer id = this.getParaToInt("id");
			String title = this.getPara("title");
			String introduce = this.getPara("introduce");
			String cover_small = this.getPara("cover_small");
			String cover_pic = this.getPara("cover_pic");
			Integer is_top = this.getParaToInt("is_top");
			Integer status = this.getParaToInt("status");
			boolean flag = false;
			uid = 1;
			if (null != id) {
				flag = specialService.update(id, uid, title, introduce, cover_small, cover_pic, is_top, status) > 0;
			} else {
				flag = specialService.save(uid, title, introduce, cover_small, cover_pic, is_top, 1);
			}
			if (flag) {
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_ERROR);
			}
			return;
		}
		this.render("edit_special");
	}

	/**
	 * 编辑专辑
	 */
	@Action("special/{mid}")
	public void edit_special() {
		Integer sid = this.getParaToInt();
		// 编辑
		if (null != sid) {
			Map<String, Object> music = specialService.getMap(null, sid);
			this.setAttr("special", music);
		}
		this.render("edit_special");
	}

	/**
	 * 禁用专辑
	 */
	@Action("special/enable")
	public void delete_special() {
		Integer sid = this.getParaToInt("sid");
		Integer status = this.getParaToInt("status");
		boolean flag = false;
		if (null != sid && null != status) {
			flag = specialService.enable(sid, status);
			if (flag) {
				this.renderText(WebConst.MSG_SUCCESS);
			} else {
				this.renderText(WebConst.MSG_FAILURE);
			}
		} else {
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
}
