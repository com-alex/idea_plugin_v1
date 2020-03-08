package com.fromLab.test;

import com.fromLab.utils.OpenprojectURL;
import org.junit.Test;

/**
 * @author wsh
 * @date 2020-03-07
 */
public class OpenProjectUtilsTest {

    @Test
    public void testOpenProject(){
        OpenprojectURL openprojectURL = new OpenprojectURL(
                "http://projects.plugininide.com/openproject/api/v3/work_packages/8",
                "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123");

    }
}
