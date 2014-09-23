package org.unique.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 音频专辑
 * @author:rex
 * @date:2014年9月23日
 * @version:1.0
 */
@Component
public class Special extends Model<Special> {
	
	private static final long serialVersionUID = 1L;
	public static Special db = new Special();
	private Integer id;
	private Integer uid;
	private String name;
	private String cover_pic;
	private Integer hit;
	private Integer create_time;
	private Integer status;

	public Special(){
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

	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

	public Integer getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getCover_pic() {
		return cover_pic;
	}

	public void setCover_pic(String cover_pic) {
		this.cover_pic = cover_pic;
	}

}

