package me.fm.service;

import java.util.List;
import java.util.Map;

import me.fm.cloud.model.User;

import org.unique.plugin.dao.Page;


/**
 * 用户接口
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
public interface UserService {

	/**
	 * 根据uid查询用户
	 * @param uid
	 * @return
	 */
	User getByUid(Integer uid);
	
	/**
	 * 查询user
	 * @param email
	 * @return
	 */
	User get(String email, Integer status);
	
	/**
	 * 根据email查询用户是否重复
	 * @param email
	 * @return
	 */
	boolean exists(String email);
	
	/**
	 * 用户注册
	 * @param username
	 * @param email
	 * @param password
	 * @return
	 */
	User register(String username, String email, String password, String ip);
	
	/**
	 * 用户登录
	 * @param email
	 * @param password
	 * @return
	 */
	User login(String email, String password);
	
	/**
	 * 根据email/uid删除用户
	 * @param email
	 * @param uid
	 * @return
	 */
	int delete(String email, Integer uid);
	
	/**
	 * 根据uids批量删除用户
	 * @param uids
	 * @return
	 */
	int deleteBatch(String uids);
	
	/**
	 * 禁用/启用用户
	 * @param email
	 * @param uid
	 * @return
	 */
	int enable(String email, Integer uid, Integer status);
	
	/**
	 * 根据查询条件获取用户列表
	 * @param username
	 * @param email
	 * @param status
	 * @param order
	 * @return
	 */
	List<User> getList(String username, String email, Integer status, String order);
	
	/**
	 * 根据查询条件分页获取用户列表
	 * @param username
	 * @param email
	 * @param status
	 * @param page
	 * @param pageSize
	 * @param order
	 * @return
	 */
	Page<User> getPageList(String username, String email, Integer status, Integer page, Integer pageSize, String order);
	
	Page<Map<String, Object>> getPageMapList(String username, String email, Integer status, Integer page, Integer pageSize, String order);
	
	/**
	 * 增加剩余空间
	 * @param useSpace
	 * @return
	 */
	int updateUseSize(Integer uid, Long useSpace);
	
	int update(Integer uid, String email, String nickName, Long space_size, Integer status);

	/**
	 * 第三方登录
	 * @param openid
	 * @param type
	 * @return
	 */
	User openLogin(String openid, Integer type);
	
	/**
	 * 绑定openid
	 * @param type
	 * @param openid
	 * @param nickName
	 * @param email
	 * @param ip
	 * @return
	 */
	User openBind(Integer type, String openid, String nickName, String email, String ip);

	/**
	 * 获取user map
	 * @param user
	 * @param uid
	 * @return
	 */
	Map<String, Object> getMap(User user, Integer uid);
	
}
