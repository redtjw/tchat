package com.tjw.tchat.service;

import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.pojo.bo.UsersLoginRquest;

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
}
