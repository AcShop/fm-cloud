package me.fm.service;

import java.util.List;

import me.fm.cloud.model.Post;

import org.unique.plugin.dao.Page;

/**
 * 文章接口
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
public interface PostService {

	/**
	 * 根据postid查询文章
	 * @param pid
	 * @return
	 */
	Post getByPid(Integer pid);
	
	/**
	 * 查询文章列表
	 * @param uid
	 * @param title
	 * @param tag
	 * @param is_pub
	 * @param order
	 * @return
	 */
	List<Post> getList(Integer uid, String title, String tag, Integer is_pub, String order);
	
	/**
	 * 分页查询文章列表
	 * @param uid
	 * @param title
	 * @param tag
	 * @param is_pub
	 * @param page
	 * @param pageSize
	 * @param order
	 * @return
	 */
	Page<Post> getPageList(Integer uid, String title, String tag, Integer is_pub, Integer page, Integer pageSize, String order);
	
	/**
	 * 删除文章
	 * @param pid
	 * @return
	 */
	int delete(Integer pid);
	
	/**
	 * 批量删除文章
	 * @param pids
	 * @return
	 */
	int delete(String pids);
	
	/**
	 * 更新文章
	 * @param pid
	 * @param title
	 * @param content
	 * @param tags
	 * @param allow_comment
	 * @param is_pub
	 * @return
	 */
	int update(Integer pid, String title, String content, String tags, Integer allow_comment, Integer is_pub);
	
	/**
	 * 保存文章
	 * @param uid
	 * @param title
	 * @param content
	 * @param tags
	 * @param allow_comment
	 * @param is_pub
	 * @return
	 */
	int save(Integer uid, String title, String content, String tags, Integer allow_comment, Integer is_pub);
	
	/**
	 * 更新阅读量
	 * @param pid
	 * @return
	 */
	int updateHit(Integer pid);
}
