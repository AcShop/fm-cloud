package org.unique.cloud.service;

import java.util.List;
import java.util.Map;

import org.unique.cloud.model.Special;
import org.unique.plugin.dao.Page;

/**
 * 专辑接口
 * @author:rex
 * @date:2014年9月23日
 * @version:1.0
 */
public interface SpecialService {

	Special get(Integer sid);

	Map<String, Object> getMap(Special special, Integer sid);

	boolean save(Integer uid, String title, String cover_pic);

	int update(Integer id, String title, Integer hit, String cover_pic);

	List<Map<String, Object>> getList(Integer uid, String title, String order);

	Page<Special> getPageList(Integer uid, String title, Integer page, Integer pageSize, String order);

	Page<Map<String, Object>> getPageMapList(Integer uid, String title, Integer page, Integer pageSize, String order);

	int delete(Integer id);
}
