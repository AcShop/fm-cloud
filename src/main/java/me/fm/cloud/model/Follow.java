package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 关注
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Follow extends Model<Follow> {
	
	private static final long serialVersionUID = 1L;
	public static Follow db = new Follow();
	private Integer id;
	private Integer uid;
	private Integer follow_uid;
	private Integer type;
	private Integer add_time;

	public Follow(){
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

	public Integer getFollow_uid() {
		return follow_uid;
	}

	public void setFollow_uid(Integer follow_uid) {
		this.follow_uid = follow_uid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}
	
}

