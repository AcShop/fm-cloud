package me.fm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.fm.api.QiniuApi;
import me.fm.api.QiniuConst;
import me.fm.service.FileService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.FileUtil;
import org.unique.ioc.annotation.Service;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.BatchStatRet;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.EntryPath;
import com.qiniu.api.rs.EntryPathPair;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;

@Service
public class FileServiceImpl implements FileService {

	private Logger logger = Logger.getLogger(FileServiceImpl.class);
	
	@Override
	public List<ListItem> getList(String prefix) {

		RSFClient client = QiniuApi.getRSFClient();
		String marker = "";

		List<ListItem> all = new ArrayList<ListItem>();
		ListPrefixRet ret = null;
		while (true) {
			ret = client.listPrifix(QiniuConst.BUCKETNAME, prefix, marker, -1);
			marker = ret.marker;
			all.addAll(ret.results);
			if (!ret.ok()) {
				// no more items or error occurs
				break;
			}
		}
		if (ret.exception.getClass() != RSFEofException.class) {
			// error handler
		}
		return all;
	}

	@Override
	public Entry getInfo(String key) {
        RSClient client = QiniuApi.getRSCClient();
        Entry statRet = client.stat(QiniuConst.BUCKETNAME, key);
        return statRet;
	}

	@Override
	public int copy(String keySrc, String keyDest) {
        RSClient client = QiniuApi.getRSCClient();
        return client.copy(QiniuConst.BUCKETNAME, keySrc, QiniuConst.BUCKETNAME, keyDest).getStatusCode();
	}

	@Override
	public int move(String keySrc, String keyDest) {
		RSClient client = QiniuApi.getRSCClient();
		return client.move(QiniuConst.BUCKETNAME, keySrc, QiniuConst.BUCKETNAME, keyDest).getStatusCode();
	}

	@Override
	public int delete(String key) {
		RSClient client = QiniuApi.getRSCClient();
        return client.delete(QiniuConst.BUCKETNAME, key).getStatusCode();
	}

	@Override
	public BatchStatRet batchGetInfo(Set<String> keys) {
		if(!CollectionUtil.isEmpty(keys)){
			List<EntryPath> entries = new ArrayList<EntryPath>();
			RSClient client = QiniuApi.getRSCClient();
			for(String key : keys){
				EntryPath e = new EntryPath();
		        e.bucket = QiniuConst.BUCKETNAME;
		        e.key = key;
		        entries.add(e);
			}
			return client.batchStat(entries);
		}
		return null;
	}

	@Override
	public int batchCopy(Map<String, String> keys) {
		if(!CollectionUtil.isEmpty(keys)){
			
			List<EntryPathPair> entries = new ArrayList<EntryPathPair>();
			RSClient client = QiniuApi.getRSCClient();
			
			for(String key : keys.keySet()){
				
				EntryPathPair pair = new EntryPathPair();
		        EntryPath src = new EntryPath();
		        src.bucket = QiniuConst.BUCKETNAME;
		        src.key = key;

		        EntryPath dest = new EntryPath();
		        dest.bucket = QiniuConst.BUCKETNAME;
		        dest.key = keys.get(key);

		        pair.src = src;
		        pair.dest = dest;
		        
		        entries.add(pair);
			}
			return client.batchCopy(entries).getStatusCode();
		}
		return 0;
	}

	@Override
	public int batchMove(Map<String, String> keys) {
		if(!CollectionUtil.isEmpty(keys)){
			
			List<EntryPathPair> entries = new ArrayList<EntryPathPair>();
			RSClient client = QiniuApi.getRSCClient();
			
			for(String key : keys.keySet()){
				
				EntryPathPair pair = new EntryPathPair();
		        EntryPath src = new EntryPath();
		        src.bucket = QiniuConst.BUCKETNAME;
		        src.key = key;

		        EntryPath dest = new EntryPath();
		        dest.bucket = QiniuConst.BUCKETNAME;
		        dest.key = keys.get(key);

		        pair.src = src;
		        pair.dest = dest;
		        
		        entries.add(pair);
			}
			return client.batchMove(entries).getStatusCode();
		}
		return 0;
	}

	@Override
	public int batchDelete(Set<String> keys) {
		if(!CollectionUtil.isEmpty(keys)){
			List<EntryPath> entries = new ArrayList<EntryPath>();
			RSClient client = QiniuApi.getRSCClient();
			for(String key : keys){
				EntryPath e = new EntryPath();
		        e.bucket = QiniuConst.BUCKETNAME;
		        e.key = key;
		        entries.add(e);
			}
			return client.batchDelete(entries).getStatusCode();
		}
		return 0;
	}

	@Override
	public int upload(String key, String filePath) {
		try {
			if(!FileUtil.exists(filePath) || StringUtils.isBlank(key)){
				return 0;
			}
			PutPolicy putPolicy = new PutPolicy(QiniuConst.BUCKETNAME);
	        Mac mac = new Mac(QiniuConst.ACCESS_KEY, QiniuConst.SECRET_KEY);
			String uptoken = putPolicy.token(mac);
			PutExtra extra = new PutExtra();
			PutRet ret = IoApi.putFile(uptoken, key, filePath, extra);
			return ret.getStatusCode();
		} catch (AuthException e) {
			logger.warn("upload file auth error " + e.getMessage());
		} catch (JSONException e) {
			logger.warn("upload file json error " + e.getMessage());
		}
		return 0;
	}

}
