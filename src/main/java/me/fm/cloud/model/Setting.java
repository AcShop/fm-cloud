package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 相册
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Setting extends Model<Setting> {
	
	private static final long serialVersionUID = 1L;
	public static Setting db = new Setting();
	private Integer id;
	private String key;
	private String value;

	public Setting(){
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

