package me.fm.service;

import java.util.List;
import java.util.Map;

import me.fm.cloud.model.Music;

import org.unique.plugin.dao.Page;



/**
 * 音乐接口
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
public interface MusicService {

	/**
	 * 获取一首音乐
	 * @param id
	 * @return
	 */
	Music get(Integer mid);
	
	/**
	 * 查询音乐map数据
	 * @param music
	 * @param mid
	 * @return
	 */
	Map<String, Object> getMap(Music music, Integer mid);
	
	/**
	 * 上传一首歌曲
	 * @param uid
	 * @param singer
	 * @param song
	 * @param song_path
	 * @param cover_path
	 * @param introduce
	 * @param cids
	 */
	boolean save(Integer uid, String singer, String song, String song_path, String cover_path, 
			String introduce, String cids, String lrc, String tags, Integer sid);
	
	/**
	 * 修改音乐信息
	 * @param singer
	 * @param song_path
	 * @param cover_path
	 * @param introduce
	 * @param cids
	 * @return
	 */
	int update(Integer id, String singer, String song, String song_path, String cover_path, String introduce,
			String cids, String lrc, String tags, Integer sid);
	
	/**
	 * 查询音乐列表
	 * @param singer
	 * @param song
	 * @param order
	 * @return
	 */
	List<Map<String, Object>> getList(Integer uid, String singer, String song, String tag, Integer sid, String order);
	
	/**
	 * 随机获取音乐
	 * @param count
	 * @return
	 */
	List<Map<String, Object>> getRandom(Integer count);
	
	/**
	 * 分页查询音乐列表
	 * @param singer
	 * @param song
	 * @param page
	 * @param pageSize
	 * @param order
	 * @return
	 */
	Page<Music> getPageList(Integer uid, String singer, String song, String tag, Integer sid, Integer status, 
			Integer page, Integer pageSize, String order);
	
	Page<Map<String, Object>> getPageMapList(Integer uid, String singer, String song, String tag, 
			Integer sid, Integer status, Integer page, Integer pageSize, String order);
	
	/**
	 * 根据音乐id
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	
	/**
	 * 设置音乐状态
	 * @param mid
	 * @param status
	 * @return
	 */
	boolean enable(Integer mid, Integer status);
	
	/**
	 * 根据音乐id批量删除音乐
	 * @param ids
	 * @return
	 */
	int delete(String ids);
	
	/**
	 * 喜欢一首音乐
	 * @param id
	 */
	void like(Integer id, Integer type);
	
}
