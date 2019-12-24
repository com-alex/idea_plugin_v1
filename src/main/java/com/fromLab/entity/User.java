package com.fromLab.entity;

import com.fromLab.annotation.Column;
import com.fromLab.annotation.Table;

/**
 * @author wsh
 * @date 2019-12-09
 * 用于测试的实体映射类
 *  注意：
 *      数据库表中不能有自增主键，并且主键要放在类的第一位
 *      实体映射类上必须加@Table注解
 *      属性上必须加@Column注解，并且里面column值要等于数据库列名
 *
 *
 */


@Table(tableName = "trello_user")
public class User {

    @Column(column = "uid")
    private Integer uid;

    @Column(column = "username")
    private String username;

    @Column(column = "password")
    private String password;

    public User() {
    }

    public User(Integer uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
