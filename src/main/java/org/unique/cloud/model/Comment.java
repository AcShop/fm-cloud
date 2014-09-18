package org.unique.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 评论
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Comment extends Model<Comment> {
	
	private static final long serialVersionUID = 1L;
	public static Comment db = new Comment();
	private Integer id;
	private Integer aid;
	private Integer type;
	private Integer uid;
	private String username;
	private String ip;
	private String content;
	private Integer create_time;

	public Comment(){
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	
	public Integer getAid() {
		return this.aid;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public Integer getUid() {
		return this.uid;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	
	public Integer getCreate_time() {
		return this.create_time;
	}

}

