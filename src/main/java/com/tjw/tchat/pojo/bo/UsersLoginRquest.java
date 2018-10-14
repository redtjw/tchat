package com.tjw.tchat.pojo.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersLoginRquest implements Serializable {
    private static final long serialVersionUID = -3452739236994267719L;
    private String username;
    private String password;
    private String cid;
}