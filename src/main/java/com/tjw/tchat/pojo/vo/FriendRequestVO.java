package com.tjw.tchat.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendRequestVO implements Serializable {
    private static final long serialVersionUID = -6833965030324641952L;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;
    private String sendUserId;
}