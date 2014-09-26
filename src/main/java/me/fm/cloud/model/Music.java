package me.fm.cloud.model;

import org.unique.ioc.annotation.Component;
import org.unique.plugin.dao.Model;

/**
 * 音乐
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Component
public class Music extends Model<Music> {
	
	private static final long serialVersionUID = 1L;
	public static Music db = new Music();
	private Integer id;
	private Integer uid;
	private String singer;
	private String song;
	private String song_path;
	private String cover_path;
	private String introduce;
	private String cids;
	private String lrc;
	private String tags;
	private Integer sid;
	private Integer listen_count;
	private Integer like_count;
	private Integer download_count;
	private Integer create_time;
	private Integer status;

	public Music(){
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
	
	public void setSinger(String singer) {
		this.singer = singer;
	}
	
	public String getSinger() {
		return this.singer;
	}
	
	public void setSong(String song) {
		this.song = song;
	}
	
	public String getSong() {
		return this.song;
	}
	
	public String getSong_path() {
		return song_path;
	}

	public void setSong_path(String song_path) {
		this.song_path = song_path;
	}
	
	public String getCover_path() {
		return cover_path;
	}

	public void setCover_path(String cover_path) {
		this.cover_path = cover_path;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public void setCids(String cids) {
		this.cids = cids;
	}
	
	public String getCids() {
		return this.cids;
	}
	
	public void setListen_count(Integer listen_count) {
		this.listen_count = listen_count;
	}
	
	public Integer getListen_count() {
		return this.listen_count;
	}
	
	public void setLike_count(Integer like_count) {
		this.like_count = like_count;
	}
	
	public Integer getLike_count() {
		return this.like_count;
	}
	
	public void setDownload_count(Integer download_count) {
		this.download_count = download_count;
	}
	
	public Integer getDownload_count() {
		return this.download_count;
	}
	
	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}
	
	public Integer getCreate_time() {
		return this.create_time;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLrc() {
		return lrc;
	}

	public void setLrc(String lrc) {
		this.lrc = lrc;
	}

}

