package me.fm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qiniu.api.rs.BatchStatRet;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rsf.ListItem;

/**
 * 文件管理接口
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
public interface FileService {

	/**
	 * 获取指定前缀的文件列表
	 * @param prefix
	 * @return
	 */
	List<ListItem> getList(String prefix);
	
	/**
	 * 根据key获取文件信息
	 * @param key
	 * @return
	 */
	Entry getInfo(String key);
	
	/**
	 * 复制单个文件
	 * @param keySrc
	 * @param keyDest
	 * @return 返回200成功
	 */
	int copy(String keySrc, String keyDest);
	
	/**
	 * 移动单个文件
	 * @param keySrc
	 * @param keyDest
	 * @return 返回200成功
	 */
	int move(String keySrc, String keyDest);
	
	/**
	 * 删除单个文件
	 * @param key
	 * @return 返回200成功
	 */
	int delete(String key);
	
	/**
	 * 批量获取文件属性
	 * @param keys
	 * @return
	 */
	BatchStatRet batchGetInfo(Set<String> keys);
	
	/**
	 * 批量复制文件
	 * @param keys
	 * @return
	 */
	int batchCopy(Map<String, String> keys);
	
	/**
	 * 批量移动文件
	 * @param keys
	 * @return
	 */
	int batchMove(Map<String, String> keys);
	
	/**
	 * 批量删除文件
	 * @param keys
	 * @return
	 */
	int batchDelete(Set<String> keys);
	
	/**
	 * 上传文件
	 * @param key key名称
	 * @param filePath 物理路径
	 * @return
	 */
	void upload(String key, String filePath);
}
