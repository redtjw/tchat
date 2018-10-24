package com.tjw.tchat.service;

import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.pojo.bo.UsersLoginRquest;
import com.tjw.tchat.pojo.vo.FriendRequestVO;

import java.util.List;

/**
 * @author Mr.Tang
 * @date 2018/10/14
 */
public interface UserServiceI {
    /**
     * 查询用户名是否存在
     * @param userName
     * @return
     */
    Boolean isUserExist(String userName);

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    Users userLogin(String userName,String password) throws Exception;

    /**
     * 注册
     * @return
     */
    Users register(Users users) throws Exception;

    /**
     * 修改
     * @param users
     */
    Users updateUserInfo(Users users);

    Users getById(String userId);

    /**
     * 添加好友的前置条件
     * @param myUserId
     * @param friendUserName
     * @return
     */
    Integer preConditionSerachFriend(String myUserId,String friendUserName);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    Users getUserByName(String userName);

    /**
     * 发送添加好友请求
     * @param userId
     * @param userName
     * @return
     */
    Boolean sendAddFriendRequest(String userId,String userName);

    /**
     * 查询好友请求列表
     * @param userId
     * @return
     */
    List<FriendRequestVO> listFriendRequest(String userId);
}
