package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 云文件
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class File extends Model<File> {
	
	private static final long serialVersionUID = 1L;
	public static File db = new File();
	private Integer id;
	private Integer uid;
	private String path;
	private Integer type;
	private Integer create_time;

	public File(){
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
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	
	public Integer getCreate_time() {
		return this.create_time;
	}

}

