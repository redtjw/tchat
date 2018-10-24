package com.tjw.tchat.service.impl;

import com.tjw.tchat.enums.SearchFriendsStatusEnum;
import com.tjw.tchat.mapper.FriendsMapper;
import com.tjw.tchat.mapper.RequestMapper;
import com.tjw.tchat.mapper.UsersCustormMapper;
import com.tjw.tchat.mapper.UsersMapper;
import com.tjw.tchat.pojo.Friends;
import com.tjw.tchat.pojo.Request;
import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.pojo.vo.FriendRequestVO;
import com.tjw.tchat.service.UserServiceI;
import com.tjw.tchat.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
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
    @Autowired
    private QRCodeUtils qrCodeUtils;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private FriendsMapper friendsMapper;
    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private UsersCustormMapper usersCustormMapper;
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
        String id = UUIDUtil.uuid();
        users.setFaceImage("");
        users.setFaceImageBig("");
        // 为用户生成二维码
        // 二维码格式 tchat_qrcode todo 加密
        String filepath = "E://Tchat/user"+id+"qrcode.png";
        qrCodeUtils.createQRCode(filepath,"tchat_qrcode:"+users.getUsername());
        MultipartFile multipartFile =FileUtils.fileToMultipart(filepath);
        String qrCode = fastDFSClient.uploadQRCode(multipartFile);
        users.setQrcode(qrCode);
        users.setId(id);
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

    @Override
    public Integer preConditionSerachFriend(String myUserId, String friendUserName) {
        // 1 搜索用户
        Users users = this.getUserByName(friendUserName);
        if (users==null){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        // 不能添加自己
        if (users.getId().equals(myUserId)){
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        // 是否已经是好友
        Example example = new Example(Friends.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("myUserId",myUserId);
        criteria.andEqualTo("friendUserId",users.getId());
        Friends friends = friendsMapper.selectOneByExample(example);
        if (friends!=null){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Override
    public Users getUserByName(String userName) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userName);
        return usersMapper.selectOneByExample(example);
    }

    @Override
    public Boolean sendAddFriendRequest(String userId, String friendName) {
        // 首先根据用户名查询用户
        Users friend = this.getUserByName(friendName);
        // 查询是否已经发送过请求了
        if (friend==null){
            return Boolean.FALSE;
        }
        Example example = new Example(Request.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sendUserId",userId);
        criteria.andEqualTo("acceptUserId",friend.getId());
        Request request = requestMapper.selectOneByExample(example);
        // 第一次添加
        if (request==null){
            Request record = new Request();
            record.setId(UUIDUtil.uuid());
            record.setSendUserId(userId);
            record.setAcceptUserId(friend.getId());
            record.setRequestDateTime(new Date());
            requestMapper.insert(record);
        }
        return true;
    }

    @Override
    public List<FriendRequestVO> listFriendRequest(String userId) {
        return usersCustormMapper.listFriendRequest(userId);
    }

}
