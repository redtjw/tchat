package com.tjw.tchat.pojo;

import javax.persistence.*;

@Table(name = "tz_friends")
public class Friends {
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "my_user_id")
    private String myUserId;

    /**
     * 用户的好友id
     */
    @Column(name = "friend_user_id")
    private String friendUserId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return my_user_id - 用户id
     */
    public String getMyUserId() {
        return myUserId;
    }

    /**
     * 设置用户id
     *
     * @param myUserId 用户id
     */
    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    /**
     * 获取用户的好友id
     *
     * @return my_friend_user_id - 用户的好友id
     */
    public String getFriendUserId() {
        return friendUserId;
    }

    /**
     * 设置用户的好友id
     *
     * @param friendUserId 用户的好友id
     */
    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }
}