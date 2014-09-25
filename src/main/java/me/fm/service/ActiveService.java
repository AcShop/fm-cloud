package me.fm.service;

import me.fm.cloud.model.Active;


/**
 * 激活表
 * @author:rex
 * @date:2014年8月26日
 * @version:1.0
 */
public interface ActiveService {
	
	/**
	 * 插入一条激活码
	 * @param uid
	 * @param code
	 * @return
	 */
	int save(Integer uid, String code);
	
	/**
	 * 查询激活码
	 * @param uid
	 * @param code
	 * @return
	 */
	Active get(Integer uid, String code);
	
	/**
	 * 激活
	 * @param code
	 */
	void active(String code);
}
