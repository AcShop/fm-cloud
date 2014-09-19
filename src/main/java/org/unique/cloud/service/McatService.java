package org.unique.cloud.service;

import org.unique.cloud.model.Mcat;



/**
 * 音乐分类接口
 * @author:rex
 * @date:2014年9月18日
 * @version:1.0
 */
public interface McatService {

	Mcat get(Integer id);
	
}
