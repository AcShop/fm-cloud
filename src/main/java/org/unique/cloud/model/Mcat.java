package org.unique.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 音乐分类
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Mcat extends Model<Mcat> {
	
	private static final long serialVersionUID = 1L;
	public static Mcat db = new Mcat();
	private Integer id;
	private String name;
	private Integer status;

	public Mcat(){
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return this.status;
	}

}

