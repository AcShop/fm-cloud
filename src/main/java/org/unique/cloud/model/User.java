package org.unique.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 用户
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class User extends Model<User> {
	
	private static final long serialVersionUID = 1L;
	public static User db = new User();
	private Integer uid;
	private String nickname;
	private String email;
	private String password;
	private String reg_ip;
	private Integer reg_time;
	private Integer log_time;
	private Integer msg_count;
	private Integer fans_count;
	private Integer follow_count;
	private Integer space_size;
	private Integer use_size;
	private Integer status;

	public User(){
	}
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public Integer getUid() {
		return this.uid;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}
	
	public String getReg_ip() {
		return this.reg_ip;
	}
	
	public void setReg_time(Integer reg_time) {
		this.reg_time = reg_time;
	}
	
	public Integer getReg_time() {
		return this.reg_time;
	}
	
	public void setLog_time(Integer log_time) {
		this.log_time = log_time;
	}
	
	public Integer getLog_time() {
		return this.log_time;
	}
	
	public void setSpace_size(Integer space_size) {
		this.space_size = space_size;
	}
	
	public Integer getSpace_size() {
		return this.space_size;
	}
	
	public void setUse_size(Integer use_size) {
		this.use_size = use_size;
	}
	
	public Integer getUse_size() {
		return this.use_size;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public Integer getMsg_count() {
		return msg_count;
	}

	public void setMsg_count(Integer msg_count) {
		this.msg_count = msg_count;
	}

	public Integer getFans_count() {
		return fans_count;
	}

	public void setFans_count(Integer fans_count) {
		this.fans_count = fans_count;
	}

	public Integer getFollow_count() {
		return follow_count;
	}

	public void setFollow_count(Integer follow_count) {
		this.follow_count = follow_count;
	}

}

