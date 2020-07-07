package com.example.sample;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author zhangyonghong
 * @date 2020.7.7
 */
@Entity
public class ObjectBoxUserInf {

    @Id
    private Long id;

    private String userName;

    private String pass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "ObjectBoxUserInf{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
