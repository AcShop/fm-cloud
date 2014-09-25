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
public class Album extends Model<Album> {
	
	private static final long serialVersionUID = 1L;
	public static Album db = new Album();
	private Integer id;
	private Integer uid;
	private String name;
	private String path;
	private Integer pic_count;
	private Integer create_time;

	public Album(){
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public Integer getUid() {
		return this.uid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPic_count(Integer pic_count) {
		this.pic_count = pic_count;
	}
	
	public Integer getPic_count() {
		return this.pic_count;
	}
	
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	
	public Integer getCreate_time() {
		return this.create_time;
	}

}

