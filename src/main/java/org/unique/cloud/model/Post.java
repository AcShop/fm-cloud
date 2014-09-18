package org.unique.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 文章
 * @author:rex
 * @date:2014年8月20日
 * @version:1.0
 */
@Component
public class Post extends Model<Post>{

	private static final long serialVersionUID = 1454835930517795587L;
	public static Post db = new Post();
	
	private Integer pid;
	private Integer uid;
	private String title;
	private String tags;
	private String content;
	private Integer allow_comment;
	private Integer hit;
	private Integer create_time;
	private Integer last_time;
	private Integer is_pub;
	
	public Post() {
		// TODO Auto-generated constructor stub
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAllow_comment() {
		return allow_comment;
	}

	public void setAllow_comment(Integer allow_comment) {
		this.allow_comment = allow_comment;
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

	public Integer getLast_time() {
		return last_time;
	}

	public void setLast_time(Integer last_time) {
		this.last_time = last_time;
	}

	public Integer getIs_pub() {
		return is_pub;
	}

	public void setIs_pub(Integer is_pub) {
		this.is_pub = is_pub;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
