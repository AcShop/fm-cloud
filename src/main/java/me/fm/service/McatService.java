package me.fm.service;

import java.util.List;

import me.fm.cloud.model.Mcat;



/**
 * 音乐分类接口
 * @author:rex
 * @date:2014年9月18日
 * @version:1.0
 */
public interface McatService {

	Mcat get(Integer id);
	
	List<Mcat> getList(Integer status);

	boolean save(String name);
	
	boolean update(Integer id, String name, Integer status);
	
}
