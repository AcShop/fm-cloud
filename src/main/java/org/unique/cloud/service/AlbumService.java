package org.unique.cloud.service;

import java.util.List;

import org.unique.cloud.model.Album;
import org.unique.plugin.dao.Page;

/**
 * 相册接口
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
public interface AlbumService {

	/**
	 * 查询一个相册
	 * @param id
	 * @return
	 */
	Album get(Integer id);
	
	/**
	 * 查询相册列表
	 * @param uid
	 * @param name
	 * @param order
	 * @return
	 */
	List<Album> getList(Integer uid, String name, String order);
	
	/**
	 * 分页查询相册列表
	 * @param uid
	 * @param name
	 * @param page
	 * @param pageSize
	 * @param order
	 * @return
	 */
	Page<Album> getPageList(Integer uid, String name, Integer page, Integer pageSize, String order);
	
	/**
	 * 新增一个相册
	 * @param uid
	 * @param name
	 * @return
	 */
	int save(Integer uid, String name, String path);
	
	/**
	 * 删除一个相册(同时会删除照片)
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	
	/**
	 * 更新相册名称/图片数
	 * @param id
	 * @param name
	 * @param count
	 * @return
	 */
	int update(Integer id, String name, Integer count);
	
}
