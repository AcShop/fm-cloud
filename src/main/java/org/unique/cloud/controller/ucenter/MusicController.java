package org.unique.cloud.controller.ucenter;

import java.util.Map;

import org.unique.cloud.controller.BaseController;
import org.unique.cloud.model.Music;
import org.unique.cloud.service.MusicService;
import org.unique.cloud.util.WebConst;
import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.dao.Page;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Path;

/**
 * 音乐管理
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/ucenter/music")
public class MusicController extends BaseController {

	@Autowired
	private MusicService musicService;
	
	/**
	 * 音乐列表
	 */
	public void index(){
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		Page<Music> musicPage = musicService.getPageList(uid, singer, song, page, pageSize, "create_time");
		this.setAttr("page", musicPage);
		this.render("index");
	}
	
	/**
	 * 上传/编辑音乐页
	 */
	@Action("save_page/{mid}")
	public void save_page(){
		Integer mid = this.getParaToInt();
		// 编辑
		if(null != mid){
			Map<String, Object> music = musicService.getMap(null, mid);
			this.setAttr("music", music);
		}
		this.render("upload");
	}
	
	/**
	 * 保存音乐
	 */
	public void save(){
		Integer mid = this.getParaToInt("mid");
		String singer = this.getPara("singer");
		String song = this.getPara("song");
		String song_path = this.getPara("song_path");
		String cover_path = this.getPara("cover_path");
		String introduce = this.getPara("introduce");
		String cids = this.getPara("cids");
		boolean flag = false;
		
		if(null != mid){
			flag = musicService.save(uid, singer, song, song_path, cover_path, introduce, cids);
		} else{
			flag = musicService.save(uid, singer, song, song_path, cover_path, introduce, cids);
		}
		
		if(flag){
			this.renderText(WebConst.MSG_SUCCESS);
		} else{
			this.renderText(WebConst.MSG_ERROR);
		}
	}
	
	/**
	 * 删除音乐
	 */
	public void remove(){
		Integer mid = this.getParaToInt();
		int count = musicService.delete(mid);
		if(count > 0){
			this.renderText(WebConst.MSG_SUCCESS);
		} else{
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
}
