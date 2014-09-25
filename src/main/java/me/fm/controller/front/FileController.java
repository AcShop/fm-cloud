package me.fm.controller.front;

import me.fm.controller.BaseController;

import org.unique.web.annotation.Path;

/**
 * 文件管理
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/file")
public class FileController extends BaseController {

	public void index(){
		System.out.println("index////");
		this.render("index");
	}
	
	public void files(){
		
	}
}
