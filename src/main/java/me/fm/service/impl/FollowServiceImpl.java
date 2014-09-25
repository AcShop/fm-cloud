package me.fm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.fm.service.FollowService;

import org.unique.common.tools.CollectionUtil;
import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Service;
import org.unique.plugin.cache.Cache;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private Cache redis;

    /**
     * uid关注to_uid
     */
    @Override
    public void follow(String uid, String to_uid) {
        // 添加到set
        this.redis.sadd("set:user:" + uid + ":following", to_uid);
        this.redis.sadd("set:user:" + to_uid + ":follower", uid);

        // 添加到list
        this.redis.lpush("list:user:" + uid + ":following", to_uid);
        this.redis.lpush("list:user:" + to_uid + ":follower", to_uid);
    }

    /**
     * uid取消对to_uid的关注
     */
    @Override
    public void unfollow(String uid, String to_uid) {
        // 删除set
        this.redis.srem("set:user:" + uid + ":following", to_uid);
        this.redis.srem("set:user:" + to_uid + ":follower", uid);

        // 删除list
        this.redis.ldel("list:user:" + uid + ":following", to_uid);
        this.redis.ldel("list:user:" + to_uid + ":follower", uid);
    }

    /**
     * uid的关注列表
     */
    @Override
    public Set<String> followingSet(String uid) {
        Set<String> sets = this.redis.smembers("set:user:" + uid + ":following");
        if (!CollectionUtil.isEmpty(sets)) {
            return sets;
        }
        List<String> list = this.redis.listGetAll("list:user:" + uid + ":following");
        if (!CollectionUtil.isEmpty(list)) {
            return new HashSet<String>(list);
        }
        return null;
    }

    /**
     * uid的粉丝列表
     */
    @Override
    public Set<String> followerSet(String uid) {
        Set<String> sets = this.redis.smembers("set:user:" + uid + ":follower");
        if (!CollectionUtil.isEmpty(sets)) {
            return sets;
        }
        List<String> list = this.redis.listGetAll("list:user:" + uid + ":follower");
        if (!CollectionUtil.isEmpty(list)) {
            return new HashSet<String>(list);
        }
        return null;
    }

    /**
     * uid是否关注to_uid
     */
    @Override
    public boolean isfollowing(String uid, String to_uid) {
        return this.redis.sismember("set:user:" + uid + ":following", to_uid);
    }

    /**
     * uid是to_uid的粉丝
     */
    @Override
    public boolean follower(String uid, String to_uid) {
        return this.redis.sismember("set:user:" + uid + ":follower", to_uid);
    }

    /**
     * uid的关注数
     */
    @Override
    public Long followingCount(String uid) {
        return this.redis.scard("set:user:" + uid + ":following");
    }

    /**
     * uid的粉丝数
     */
    @Override
    public Long followerCount(String uid) {
        return this.redis.scard("set:user:" + uid + ":follower");
    }

    /**
     * uid和to_uid共同关注
     */
    @Override
    public Set<String> commonfollowing(String uid, String to_uid) {
        return this.redis.sinter("set:user:" + uid + ":following", "set:user:" + to_uid + ":following");
    }

    /**
     * uid和to_uid共同粉丝
     */
    @Override
    public Set<String> commonfollower(String uid, String to_uid) {
        return this.redis.sinter("set:user:" + uid + ":follower", "set:user:" + to_uid + ":follower");
    }

    @Override
    public List<String> getFans(String uid, int page, int pageSize) {
        return this.redis.listRange("list:user:" + uid + ":following", page, pageSize);
    }

    @Override
    public List<String> getFllow(String uid, int page, int pageSize) {
        return this.redis.listRange("list:user:" + uid + ":follower", page, pageSize);
    }

}
