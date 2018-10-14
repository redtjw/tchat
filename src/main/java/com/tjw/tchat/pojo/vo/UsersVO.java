package com.tjw.tchat.pojo.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UsersVO implements Serializable {
    private static final long serialVersionUID = -6833965030324641952L;
    private String username;
    private String faceImage;
    private String faceImageBig;
    private String nickname;
    private String qrcode;
    private String cid;
    private String id;
}