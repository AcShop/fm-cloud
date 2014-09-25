package me.fm.service.impl;

import java.util.Date;
import java.util.List;

import me.fm.cloud.model.Album;
import me.fm.cloud.model.Picture;
import me.fm.service.AlbumService;
import me.fm.service.FileService;
import me.fm.service.PictureService;
import me.fm.util.AttachUtil;

import org.apache.log4j.Logger;
import org.unique.common.tools.DateUtil;
import org.unique.common.tools.StringUtils;
import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class PictureServiceImpl implements PictureService {

	private Logger logger = Logger.getLogger(PictureServiceImpl.class);
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AlbumService albumService;
	
	@Override
	public Picture get(Integer id) {
		SqlBase base = SqlBase.select("select * from t_pic");
        base.eq("id", id);
        return Picture.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public List<Picture> getList(Integer uid, String name, Integer album_id, String order) {
		SqlBase base = SqlBase.select("select * from t_pic");
        base.eq("uid", uid).eq("name", name).eq("album_id", album_id).order(order);
        return Picture.db.findList(base.getSQL(), base.getParams());
	}

	@Override
	public Page<Picture> getPageList(Integer uid, String name, Integer album_id, Integer page, Integer pageSize,
			String order) {
		SqlBase base = SqlBase.select("select * from t_pic");
        base.eq("uid", uid).eq("name", name).eq("album_id", album_id).order(order);
        return Picture.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public int save(Integer uid, String name, String path, String introduce, Integer album_id) {
		//上传到七牛
		String random = DateUtil.convertDateToInt(new Date()) + StringUtils.randomNum(4);
		String key = AttachUtil.getPicKey(uid, path, random);
		//上传图片
		fileService.upload(key, path);
		
		//保存到数据库
		int count = 0;
		try {
			Picture.db.update("insert into t_pic(uid, name, path, introduce, album_id, create_time) values (?,?,?,?,?,?)",
					uid, name, key, introduce, album_id, DateUtil.getCurrentTime());
		} catch (UpdateException e) {
			logger.warn("保存图片失败：" + e.getMessage());
			count = 0;
		}
		if(count > 0 && null != album_id){
			//更新相册
			Album album = albumService.get(album_id);
			if(null != album){
				albumService.update(album_id, null, album.getPic_count() + 1);
			}
		}
		return count;
	}

	@Override
	public int delete(Integer id) {
		int count = 0;
		Picture pic = this.get(id);
		if(null != pic){
			//删除七牛
			fileService.delete(pic.getPath());
			//更新相册
			Album album = albumService.get(pic.getAlbum_id());
			if(null != album){
				albumService.update(pic.getAlbum_id(), null, album.getPic_count() - 1);
			}
			//删除数据库
			try {
				count = Picture.db.update("delete from t_pic where id = ?", id);
			} catch (UpdateException e) {
				logger.warn("删除图片失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int delete(Integer... ids) {
		if(null != ids && ids.length > 0){
			int count = 0;
			for(Integer id : ids){
				this.delete(id);
				count++;
			}
			return count;
		}
		return 0;
	}

	@Override
	public int update(Integer id, String name, String introduce) {
		int count = 0;
		if(null != id){
			SqlBase base = SqlBase.update("update t_pic");
			base.set("name", name).set("introduce", introduce).eq("id", id);
			try {
				count = Picture.db.update(base.getSQL(), base.getParams());
			} catch (UpdateException e) {
				logger.warn("更新图片失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

}
