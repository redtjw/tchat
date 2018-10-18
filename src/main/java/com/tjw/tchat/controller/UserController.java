package com.tjw.tchat.controller;

import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.pojo.bo.UsersBO;
import com.tjw.tchat.pojo.bo.UsersLoginRquest;
import com.tjw.tchat.pojo.vo.UsersVO;
import com.tjw.tchat.service.UserServiceI;
import com.tjw.tchat.utils.FastDFSClient;
import com.tjw.tchat.utils.FileUtils;
import com.tjw.tchat.utils.MD5Utils;
import com.tjw.tchat.utils.TChatResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private FastDFSClient fastDFSClient;
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
    @PostMapping("/uploadFaceBase64")
    TChatResponse uploadFaceBase64(@RequestBody UsersBO usersBO) throws Exception{
        // 获取前端传来的base64字符串，然后转换成对象上传
        String base64Data = usersBO.getFaceData();
        String filePath = "E:\\Tchat"+usersBO.getUserId()+"userface64.png";
        FileUtils.base64ToFile(filePath,base64Data);
        //上传文件到fastdfs
        MultipartFile multipartFile = FileUtils.fileToMultipart(filePath);
        String url = fastDFSClient.uploadBase64(multipartFile);
        System.out.println(url);
        // 获取缩略图的url
        String thump = "_80x80.";
        String arr[] = url.split("\\.");
        String thumpImageUrl = arr[0]+thump+arr[1];
        // 更新用户头像
        Users users = new Users();
        users.setId(usersBO.getUserId());
        users.setFaceImage(thumpImageUrl);
        users.setFaceImageBig(url);
        users = userService.updateUserInfo(users);
        return TChatResponse.ok(users);
    }
    @PostMapping("/changeNickname")
    TChatResponse updateNickName(@RequestBody UsersBO usersBO){
        Users users = new Users();
        users.setId(usersBO.getUserId());
        users.setNickname(usersBO.getNickname());
        users = userService.updateUserInfo(users);
        return TChatResponse.ok(users);
    }
}
