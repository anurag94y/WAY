package com.way.way.pojo;

import java.util.List;

/**
 * Created by anurag.yadav on 4/13/17.
 */
public class UserDetail {
    private String username;
    private String password;
    private String emailId;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private List<String> friendsUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getFriendsUsername() {
        return friendsUsername;
    }

    public void setFriendsUsername(List<String> friendsUsername) {
        this.friendsUsername = friendsUsername;
    }
}
