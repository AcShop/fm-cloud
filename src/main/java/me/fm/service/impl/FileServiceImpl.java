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
	public void copy(final String keySrc, final String keyDest) {
		new Thread(){
			@Override
			public void run() {
				RSClient client = QiniuApi.getRSCClient();
				client.copy(QiniuConst.BUCKETNAME, keySrc, QiniuConst.BUCKETNAME, keyDest);
			}
		}.start();
	}

	@Override
	public void move(final String keySrc, final String keyDest) {
		new Thread(){
			@Override
			public void run() {
				RSClient client = QiniuApi.getRSCClient();
				client.move(QiniuConst.BUCKETNAME, keySrc, QiniuConst.BUCKETNAME, keyDest);
			}
		}.start();
	}

	@Override
	public void delete(final String key) {
		new Thread() {
			@Override
			public void run() {
				RSClient client = QiniuApi.getRSCClient();
				client.delete(QiniuConst.BUCKETNAME, key);
			}
		}.start();
	}

	@Override
	public BatchStatRet batchGetInfo(Set<String> keys) {
		if (!CollectionUtil.isEmpty(keys)) {
			List<EntryPath> entries = new ArrayList<EntryPath>();
			RSClient client = QiniuApi.getRSCClient();
			for (String key : keys) {
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
	public void batchCopy(final Map<String, String> keys) {
		if (!CollectionUtil.isEmpty(keys)) {
			
			new Thread(){
				@Override
				public void run() {
					List<EntryPathPair> entries = new ArrayList<EntryPathPair>();
					RSClient client = QiniuApi.getRSCClient();

					for (String key : keys.keySet()) {

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
					client.batchCopy(entries);
				}
			}.start();
			
		}
	}

	@Override
	public void batchMove(final Map<String, String> keys) {
		if (!CollectionUtil.isEmpty(keys)) {
			
			new Thread() {
				@Override
				public void run() {
					List<EntryPathPair> entries = new ArrayList<EntryPathPair>();
					RSClient client = QiniuApi.getRSCClient();

					for (String key : keys.keySet()) {

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
					client.batchMove(entries);
				}
			}.start();
		}
	}

	@Override
	public void batchDelete(final Set<String> keys) {
		if (!CollectionUtil.isEmpty(keys)) {
			new Thread() {
				@Override
				public void run() {
					List<EntryPath> entries = new ArrayList<EntryPath>();
					RSClient client = QiniuApi.getRSCClient();
					for (String key : keys) {
						EntryPath e = new EntryPath();
						e.bucket = QiniuConst.BUCKETNAME;
						e.key = key;
						entries.add(e);
					}
					client.batchDelete(entries);
				}
			}.start();
		}
	}

	@Override
	public void upload(final String key, final String filePath) {
		if (FileUtil.exists(filePath) && StringUtils.isNotBlank(key)) {
			new Thread() {
				public void run() {
					PutPolicy putPolicy = new PutPolicy(QiniuConst.BUCKETNAME);
					Mac mac = new Mac(QiniuConst.ACCESS_KEY, QiniuConst.SECRET_KEY);
					String uptoken;
					try {
						uptoken = putPolicy.token(mac);
						PutExtra extra = new PutExtra();
						PutRet ret = IoApi.putFile(uptoken, key, filePath, extra);
						logger.info(key + "上传结果：" + ret.getResponse());
					} catch (AuthException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
	}

}
