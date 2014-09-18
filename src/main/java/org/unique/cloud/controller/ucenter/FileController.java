package org.unique.cloud.controller.ucenter;

import org.unique.cloud.controller.BaseController;
import org.unique.web.annotation.Path;

/**
 * 文件管理
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/ucenter/file")
public class FileController extends BaseController {

	public void index(){
		System.out.println("index////");
		this.render("index");
	}
	
	
}
