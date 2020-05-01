package com.fromLab.service;

/**
 * @author wsh
 * @date 2020-03-01
 * 用于用户的认证
 */
public interface UserService {

    //认证
    Boolean authorize(String openProjectURl, String apiKey);
}
