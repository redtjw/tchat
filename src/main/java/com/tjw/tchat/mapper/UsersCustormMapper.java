package com.tjw.tchat.mapper;


import com.tjw.tchat.pojo.Users;
import com.tjw.tchat.pojo.vo.FriendRequestVO;
import com.tjw.tchat.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersCustormMapper extends MyMapper<Users> {
    List<FriendRequestVO> listFriendRequest(@Param("userId") String userId);
}