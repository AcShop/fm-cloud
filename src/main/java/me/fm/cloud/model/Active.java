package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 激活表
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Active extends Model<Active> {
	
	private static final long serialVersionUID = 1L;
	public static Active db = new Active();
	private Integer id;
	private Integer uid;
	private Integer status;

	public Active(){
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

