package me.fm.api;

import java.util.Map;

import org.apache.log4j.Logger;
import org.unique.common.tools.PropUtil;

import com.qiniu.api.config.Config;

/**
 * 七牛参数
 * @author Rex
 *
 */
public class QiniuConst {
	
	private static Logger logger = Logger.getLogger(QiniuConst.class);
	public static String BUCKETNAME;
	public static String DOMAIN;
	public static String ACCESS_KEY;
	public static String SECRET_KEY;
	
	static{
		try {
			/**
			 * 初始化七牛配置参数
			 */
			Map<String, String> qiniu = PropUtil.getPropertyMap("qiniu.properties");
			QiniuConst.ACCESS_KEY = qiniu.get("ACCESS_KEY").trim();
			QiniuConst.SECRET_KEY = qiniu.get("SECRET_KEY").trim();
			BUCKETNAME = qiniu.get("BUCKETNAME").trim();
			DOMAIN = "http://" + BUCKETNAME + ".qiniudn.com";
			Config.UP_HOST = "http://up.qiniu.com";
		} catch (RuntimeException e) {
			logger.error("qiniu config file init error!" + e.getMessage());
		}
	}
	
}
