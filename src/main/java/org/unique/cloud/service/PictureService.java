package org.unique.cloud.service;

import java.util.List;

import org.unique.cloud.model.Picture;
import org.unique.plugin.dao.Page;

/**
 * 图片处理接口
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
public interface PictureService {

	/**
	 * 查询一个图片
	 * @param id
	 * @return
	 */
	Picture get(Integer id);
	
	/**
	 * 查询图片列表
	 * @param uid
	 * @param name
	 * @param album_id
	 * @param order
	 * @return
	 */
	List<Picture> getList(Integer uid, String name, Integer album_id, String order);
	
	/**
	 * 分页查询图片列表
	 * @param uid
	 * @param name
	 * @param album_id
	 * @param page
	 * @param pageSize
	 * @param order
	 * @return
	 */
	Page<Picture> getPageList(Integer uid, String name, Integer album_id, Integer page, Integer pageSize, String order);
	
	/**
	 * 保存一张新图片
	 * @param uid
	 * @param name
	 * @param path
	 * @param album_id
	 * @return
	 */
	int save(Integer uid, String name, String path, String introduce, Integer album_id);
	
	/**
	 * 删除一张图片
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	
	/**
	 * 批量删除一组图片
	 * @param ids
	 * @return
	 */
	int delete(Integer... ids);
	
	/**
	 * 更新图片信息
	 * @param id
	 * @param name
	 * @param introduce
	 * @return
	 */
	int update(Integer id, String name, String introduce);
	
}
