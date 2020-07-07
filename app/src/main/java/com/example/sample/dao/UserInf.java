package com.example.sample.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author zhangyonghong
 * @date 2020.7.3
 */
@Entity
public class UserInf {

    @Id(autoincrement = true)
    private Long id;

    private String userName;

    private String pass;

    @Generated(hash = 1620814028)
    public UserInf(Long id, String userName, String pass) {
        this.id = id;
        this.userName = userName;
        this.pass = pass;
    }

    @Generated(hash = 965704295)
    public UserInf() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "UserInf{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
