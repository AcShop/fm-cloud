package me.fm.service.impl;

import java.util.List;

import me.fm.cloud.model.Post;
import me.fm.service.PostService;

import org.apache.log4j.Logger;
import org.unique.common.tools.DateUtil;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.dao.Page;
import org.unique.plugin.dao.SqlBase;
import org.unique.plugin.db.exception.UpdateException;

@Service
public class PostServiceImpl implements PostService {

	private Logger logger = Logger.getLogger(PostServiceImpl.class);
	
	@Override
	public Post getByPid(Integer pid) {
		SqlBase base = SqlBase.select("select * from t_post");
		base.eq("pid", pid);
		return Post.db.find(base.getSQL(), base.getParams());
	}

	@Override
	public List<Post> getList(Integer uid, String title, String tag, Integer is_pub, String order) {
		SqlBase base = SqlBase.select("select * from t_post");
		base.eq("uid", uid).eq("title", title).in("tags", tag).eq("is_pub", is_pub).order(order);
		return Post.db.findList(base.getSQL(), base.getParams());
	}

	@Override
	public Page<Post> getPageList(Integer uid, String title, String tag, Integer is_pub, Integer page,
			Integer pageSize, String order) {
		SqlBase base = SqlBase.select("select * from t_post");
		base.eq("uid", uid).eq("title", title).in("tags", tag).eq("is_pub", is_pub).order(order);
		return Post.db.findListPage(page, pageSize, base.getSQL(), base.getParams());
	}

	@Override
	public int delete(Integer pid) {
		int count = 0;
		if (null != pid) {
			try {
				count = Post.db.delete("delete from t_post where pid = ?", pid);
			} catch (UpdateException e) {
				logger.warn("删除文章失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int delete(String pids) {
		int count = 0;
		if (null != pids) {
			try {
				count = Post.db.delete("delete from t_post where pid in (?)", pids);
			} catch (UpdateException e) {
				logger.warn("删除文章列表失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int update(Integer pid, String title, String content, String tags, Integer allow_comment, Integer is_pub) {
		int count = 0;
		if (null != pid) {
			SqlBase base = SqlBase.update("update t_post");
			base.set("title", title).set("content", content).set("tags", tags).set("allow_comment", allow_comment)
					.set("is_pub", is_pub).eq("pid", pid);
			try {
				count = Post.db.update(base.getSQL(), base.getParams());
			} catch (UpdateException e) {
				logger.warn("更新文章失败：" + e.getMessage());
				count = 0;
			}
		}
		return count;
	}

	@Override
	public int save(Integer uid, String title, String content, String tags, Integer allow_comment, Integer is_pub) {
		int count = 0;
		Integer currentTime = DateUtil.getCurrentTime();
		try {
			count = Post.db.update(
					"insert into t_post(uid, title, content, tags, allow_comment, is_pub, post_time, last_time) "
							+ "values(?,?,?,?,?,?,?,?)", uid, title, content, tags, allow_comment, is_pub, currentTime,
					currentTime);
		} catch (UpdateException e) {
			logger.warn("保存文章失败：" + e.getMessage());
			count = 0;
		}
		return count;
	}

	@Override
	public int updateHit(Integer pid) {
		if(null != pid){
			return Post.db.update("update t_post set hit = (hit + 1),last_time=? where pid=?", DateUtil.getCurrentTime(), pid);
		}
		return 0;
	}

}
