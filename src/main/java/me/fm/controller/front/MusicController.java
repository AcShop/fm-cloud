package me.fm.controller.front;

import java.util.List;
import java.util.Map;

import me.fm.controller.BaseController;
import me.fm.service.MusicService;
import me.fm.util.WebConst;

import org.unique.ioc.annotation.Autowired;
import org.unique.plugin.dao.Page;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Path;

/**
 * 前台音乐展示
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
@Path("/m")
public class MusicController extends BaseController {

	@Autowired
	private MusicService musicService;

	/**
	 * 最新10首歌
	 */
	public void new_music(){
		Page<Map<String, Object>> top10List = musicService.getPageMapList(null, null, null, null, null, 1, 1, 10, "create_time desc");
		this.renderJson(top10List);
	}
	
	/**
	 * 最热门的10首歌
	 */
	public void hot(){
		Page<Map<String, Object>> top10List = musicService.getPageMapList(null, null, null, null, null, 1, 1, 10, "like_count desc");
		this.renderJson(top10List);
	}
	
	/**
	 * 按歌名查询
	 */
	public void search(){
		String song = this.getPara("song");
		String singer = this.getPara("singer");
		Page<Map<String, Object>> top10List = musicService.getPageMapList(null, singer, song, null, null, 1, 1, 10, "like_count desc");
		this.renderJson(top10List);
	}
	
	/**
	 * 随机推荐
	 */
	public void random(){
		List<Map<String, Object>> randomList = musicService.getRandom(10);
		this.renderJson(randomList);
	}
	
	/**
	 * 删除一首音乐
	 */
	public void delete_music(){
		Integer mid = this.getParaToInt("mid");
		int count = musicService.delete(mid);
		if(count > 0){
			this.renderText(WebConst.MSG_SUCCESS);
		} else{
			this.renderText(WebConst.MSG_FAILURE);
		}
	}
	
	/**
	 * 1喜欢/2收听/3下载音乐 点击+1
	 */
	public void hit(){
		Integer mid = this.getParaToInt("mid");
		Integer type = this.getParaToInt("type");
		musicService.like(mid, type);
		this.renderText(WebConst.MSG_SUCCESS);
	}
	
	/**
	 * 查询音乐信息
	 */
	@Action("get_music")
	public void getMusic(){
		Integer mid = this.getParaToInt("mid");
		if(null != mid){
			Map<String, Object> map = musicService.getMap(null, mid);
			this.renderJson(map);
		}
	}
	
}
