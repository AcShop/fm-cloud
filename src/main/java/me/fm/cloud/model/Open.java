package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 开放平台关联表
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Open extends Model<Open> {
	
	private static final long serialVersionUID = 1L;
	public static Open db = new Open();
	private Integer id;
	private Integer type;
	private String email;
	private String openid;
	private Integer status;
	
	public Open(){
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

