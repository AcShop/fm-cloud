package me.fm.service.impl;

import java.util.List;

import me.fm.cloud.model.Album;
import me.fm.service.AlbumService;

import org.apache.log4j.Logger;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class AlbumServiceImpl implements AlbumService {

	private Logger logger = Logger.getLogger(AlbumServiceImpl.class);

	@Override
	public Album get(Integer id) {
		SqlBase base = SqlBase.select("select * from t_album");
		base.eq("id", id);
		return Album.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public List<Album> getList(Integer uid, String name, String order) {
		SqlBase base = SqlBase.select("select * from t_album");
		base.eq("uid", uid).eq("name", name).order(order);
		return Album.db.findList(base.getSQL(), base.getParams());
	}

	@Override
	public Page<Album> getPageList(Integer uid, String name, Integer page, Integer pageSize, String order) {
		SqlBase base = SqlBase.select("select * from t_album");
		base.eq("uid", uid).eq("name", name).order(order);
		return Album.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public int save(Integer uid, String name, String path) {
		int count = 0;
		try {
			count = Album.db.update("insert into t_album(uid, name, path)", uid, name, path);
		} catch (UpdateException e) {
			logger.warn("添加相册失败：" + e.getMessage());
			count = 0;
		}
		return count;
	}

	@Override
	public int delete(Integer id) {
		int count = 0;
		if (null != id) {
			try {
				count = Album.db.delete("delete from t_album where id = ?", id);
			} catch (UpdateException e) {
				logger.warn("删除相册{" + id + "}失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int update(Integer id, String name, Integer count) {
		int count_ = 0;
		if (null != id) {
			SqlBase base = SqlBase.update("update t_album");
			base.set("name", name).set("count", count).eq("id", id);
			try {
				count_ = Album.db.update(base.getSQL(), base.getParams());
			} catch (UpdateException e) {
				logger.warn("更新相册失败：" + e.getMessage());
				count_ = 0;
			}
		}
		return count_;
	}

}
