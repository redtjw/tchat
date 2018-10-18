package com.tjw.tchat.service.impl;

import com.tjw.tchat.mapper.UsersMapper;
import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.service.UserServiceI;
import com.tjw.tchat.utils.MD5Utils;
import com.tjw.tchat.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;

/**
 * @author Mr.Tang
 * @date 2018/10/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserServiceI {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public Boolean isUserExist(String userName) {
        Users users = new Users();
        users.setUsername(userName);
        Users result = usersMapper.selectOne(users);
        return !Objects.isNull(result);
    }

    @Override
    public Users userLogin(String userName,String password) throws Exception {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userName);
        criteria.andEqualTo("password",MD5Utils.getMD5Str(password));
        Users users = usersMapper.selectOneByExample(example);
        return users;
    }

    @Override
    public Users register(Users users) throws Exception{
        users.setFaceImage("");
        users.setFaceImageBig("");
        users.setQrcode("");
        users.setId(UUIDUtil.uuid());
        users.setNickname(users.getUsername());
        usersMapper.insert(users);
        return users;
    }

    @Override
    public Users updateUserInfo(Users users) {
        usersMapper.updateByPrimaryKeySelective(users);
        return this.getById(users.getId());
    }

    @Override
    public Users getById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

}
