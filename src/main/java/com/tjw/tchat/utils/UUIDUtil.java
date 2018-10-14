package com.tjw.tchat.utils;

import java.util.UUID;

/**
 * @author Mr.Tang
 * @date 2018/10/14
 */
public class UUIDUtil {
    private UUIDUtil(){}
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
