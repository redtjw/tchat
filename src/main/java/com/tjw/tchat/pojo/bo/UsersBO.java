package com.tjw.tchat.pojo.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersBO implements Serializable {
    private static final long serialVersionUID = -6833965030324641952L;
   private String userId;
   private String faceData;
   private String nickname;
}