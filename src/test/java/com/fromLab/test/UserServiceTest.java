package com.fromLab.test;

import com.fromLab.service.UserService;
import com.fromLab.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author wsh
 * @date 2020-03-01
 */
public class UserServiceTest {


    @Test
    public void testAuthorize(){
        UserService userService = new UserServiceImpl();
        System.out.println(userService.authorize("http://projects.plugininide.com/openproject/",
                "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123"));

//        System.out.println(userService.authorize("12312312312",
//                "123123123"));
    }



}
