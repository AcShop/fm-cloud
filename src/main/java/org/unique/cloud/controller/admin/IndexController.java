package org.unique.cloud.controller.admin;

import org.unique.cloud.service.SettingService;
import org.unique.ioc.annotation.Autowired;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Path;
import org.unique.web.core.Controller;

/**
 * 用户后台
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/admin")
public class IndexController extends Controller {

	@Autowired
	private SettingService settingService;
	
	public void index(){
		this.render("index");
	}
	
	@Action("music")
	public void music(){
		System.out.println("music");
	}
	
}
