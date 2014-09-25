package me.fm.service;

import java.util.List;
import java.util.Set;

/**
 * 用户关注接口
 * 
 * @author rex
 */
public interface FollowService {

    // 关注某用户
    void follow(String uid, String to_uid);

    // 取消关注某用户
    void unfollow(String uid, String to_uid);

    // 用户关注列表
    Set<String> followingSet(String uid);

    // 用户粉丝列表
    Set<String> followerSet(String uid);

    // 我是否关注uid
    boolean isfollowing(String uid, String to_uid);

    // uid是否关注我
    boolean follower(String uid, String to_uid);

    // uid的关注人数
    Long followingCount(String uid);

    // uid的粉丝人数
    Long followerCount(String uid);

    // uid和to_uid的共同关注
    Set<String> commonfollowing(String uid, String to_uid);
    
    // uid和to_uid的共同粉丝
    Set<String> commonfollower(String uid, String to_uid);
    
    // 分页获取粉丝
    List<String> getFans(String uid, int page, int pageSize);
    
    // 分页获取关注
    List<String> getFllow(String uid, int page, int pageSize);
}
