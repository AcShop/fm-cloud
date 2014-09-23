package org.unique.cloud.controller.admin;

import java.util.Map;

import org.unique.cloud.controller.BaseController;
import org.unique.cloud.service.MusicService;
import org.unique.cloud.service.SettingService;
import org.unique.cloud.service.UserService;
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
	
	public void index(){
		this.render("index");
	}
	
	/**
	 * 音乐列表
	 */
	@Action("m")
	public void music(){
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		Page<Map<String, Object>> musicPage = musicService.getPageMapList(uid, singer, song, null, null, page, pageSize, "create_time");
		this.setAttr("pageMap", musicPage);
		this.render("music");
	}
	
	/**
	 * 用户列表
	 */
	@Action("u")
	public void user(){
		Page<Map<String, Object>> userPage = userService.getPageMapList(null, null, null, page, pageSize, "uid desc");
		this.setAttr("userPage", userPage);
		this.render("index");
	}
	
	/**
	 * 启用/禁用用户
	 */
	@Action("u/enable")
	public void enable(){
		Integer status = this.getParaToInt("status");
		Integer uid = this.getParaToInt("uid");
		int count = userService.updateStatus(uid, null, status);
		if(count > 0){
			this.renderText(WebConst.MSG_SUCCESS);
		} else{
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
	
}
