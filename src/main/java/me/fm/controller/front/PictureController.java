package me.fm.controller.front;

import me.fm.controller.BaseController;
import me.fm.service.PictureService;

import org.unique.ioc.annotation.Autowired;
import org.unique.web.annotation.Path;

/**
 * 相册/图片管理
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/pic")
public class PictureController extends BaseController {

	@Autowired
	private PictureService pictureService;
	
	public void index(){
		System.out.println("index////");
		this.render("index");
	}
	
	
}
