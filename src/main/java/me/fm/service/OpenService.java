package me.fm.service;

import me.fm.cloud.model.Open;

/**
 * 第三方绑定接口
 * @author:rex
 * @date:2014年8月26日
 * @version:1.0
 */
public interface OpenService {

	/**
	 * 查询openid
	 * @param email
	 * @param openid
	 * @param type
	 * @return
	 */
	Open get(String email, String openid, Integer type);
	
	/**
	 * 绑定openid到uid上
	 * @param uid
	 * @param type
	 * @param openid
	 * @return
	 */
	int save(String email, Integer type, String openid);
	
}
