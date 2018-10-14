package com.tjw.tchat.controller;

import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.pojo.bo.UsersLoginRquest;
import com.tjw.tchat.pojo.vo.UsersVO;
import com.tjw.tchat.service.UserServiceI;
import com.tjw.tchat.utils.MD5Utils;
import com.tjw.tchat.utils.TChatResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Mr.Tang
 * @date 2018/10/14
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserServiceI userService;
    @PostMapping(value = "/registerOrlogin")
     public TChatResponse login(@RequestBody UsersLoginRquest request) throws Exception{
        if (StringUtils.isBlank(request.getUsername())||StringUtils.isBlank(request.getPassword())){
            return TChatResponse.errorMsg("用户名和密码不能为空");
        }
        // 第一步，检查用户名是否存存在，如果存在，则走登录，否则走注册流程
        Boolean flag = userService.isUserExist(request.getUsername());
        Users user = new Users();
        if (flag){
            // 登录
            user = userService.userLogin(request.getUsername(),request.getPassword());
            if (Objects.isNull(user)){
                return TChatResponse.errorMsg("用户名或者密码错误");
            }
        }else{
            // 注册
            Users u=new Users();
            u.setUsername(request.getUsername());
            u.setPassword(MD5Utils.getMD5Str(request.getPassword()));
            u.setCid(request.getCid());
            user = userService.register(u);
        }
        UsersVO usersVO = new UsersVO();
        if (user!=null){
            BeanUtils.copyProperties(user,usersVO);
        }
        return TChatResponse.ok(usersVO);
    }
}
