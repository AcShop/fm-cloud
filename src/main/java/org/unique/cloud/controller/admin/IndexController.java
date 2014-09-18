package org.unique.cloud.controller.admin;

import org.unique.cloud.controller.BaseController;
import org.unique.cloud.model.Music;
import org.unique.cloud.service.MusicService;
import org.unique.cloud.service.SettingService;
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
	private SettingService settingService;
	@Autowired
	private MusicService musicService;
	
	public void index(){
		this.render("index");
	}
	
	@Action("music")
	public void music(){
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		Page<Music> musicPage = musicService.getPageList(uid, singer, song, page, pageSize, "create_time");
		this.setAttr("page", musicPage);
		this.render("index");
	}
	
}
