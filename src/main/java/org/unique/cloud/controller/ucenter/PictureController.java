package org.unique.cloud.controller.ucenter;

import org.unique.cloud.controller.BaseController;
import org.unique.cloud.model.Picture;
import org.unique.cloud.service.PictureService;
import org.unique.cloud.util.WebConst;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.dao.Page;
import org.unique.web.annotation.Path;

/**
 * 相册/图片管理
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/ucenter/pic")
public class PictureController extends BaseController {

	@Autowired
	private PictureService pictureService;
	
	/**
	 * 图片列表
	 */
	public void index(){
		String name = this.getPara("name");
		Integer album_id = this.getParaToInt("album_id");
		Page<Picture> picPage = pictureService.getPageList(uid, name, album_id, page, pageSize, "create_time");
		this.setAttr("page", picPage);
		this.render("index");
	}
	
	/**
	 * 上传一张图片
	 */
	public void save(){
		String name = this.getPara("name");
		String introduce = this.getPara("introduce");
		String path = this.getPara("path");
		Integer album_id = this.getParaToInt("album_id");
		
		int count = pictureService.save(uid, name, path, introduce, album_id);
		if(count > 0){
			this.renderText(WebConst.MSG_SUCCESS);
		} else{
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
	
	/**
	 * 删除一张图片
	 */
	public void remove(){
		Integer pic_id = this.getParaToInt();
		int count = pictureService.delete(pic_id);
		if(count > 0){
			this.renderText(WebConst.MSG_SUCCESS);
		} else{
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
	
}
