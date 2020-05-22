package com.fromLab.test;

import com.fromLab.service.UserService;
import com.fromLab.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author wsh
 * @date 2020-05-22
 */
public class LoginTest {

    @Test
    public void login(){
        String url = "http://projects.plugininide.com/openproject";
        String apiKey = "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";
        UserService userService = new UserServiceImpl();
        Boolean authorize = userService.authorize(url, apiKey);
        System.out.println(authorize);
    }
}
