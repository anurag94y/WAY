package com.way.way.pojo;

import java.util.List;

/**
 * Created by anurag.yadav on 4/14/17.
 */

public class Friend {
    private String username;
    private List<String> friendList;

    public Friend() {
        super();
    }

    public Friend(String username, List<String> friendList) {
        this.username = username;
        this.friendList = friendList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }
}
