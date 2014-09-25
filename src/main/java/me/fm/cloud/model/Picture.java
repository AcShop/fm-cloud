package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 图片
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Picture extends Model<Picture> {
	
	private static final long serialVersionUID = 1L;
	public static Picture db = new Picture();
	private Integer id;
	private Integer uid;
	private String name;
	private String introduce;
	private String path;
	private Integer album_id;
	private Integer status;
	private Integer create_time;

	public Picture(){
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setAlbum_id(Integer album_id) {
		this.album_id = album_id;
	}
	
	public Integer getAlbum_id() {
		return this.album_id;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public Integer getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}

}

