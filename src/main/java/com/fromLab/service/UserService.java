package com.fromLab.service;

/**
 * @author wsh
 * @date 2020-03-01
 * Used for user authentication
 */
public interface UserService {

    // Authentication
    Boolean authorize(String openProjectURl, String apiKey);
}
