package me.fm.api;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rsf.RSFClient;

/**
 * 各种客户端获取工厂
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
public class QiniuApi {
	
	public static Mac getMac(){
		return new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	}
	
	public static RSClient getRSCClient(){
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        RSClient client = new RSClient(mac);
        return client;
	}
	
	public static RSFClient getRSFClient(){
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		RSFClient client = new RSFClient(mac);
        return client;
	}

	/**
	 * 根据key获取资源完整路径
	 * @param song_path
	 * @return
	 */
	public static String getUrlByKey(String key) {
		return "http://" + QiniuConst.BUCKETNAME + ".qiniudn.com/" + key;
	}
	
}
