package com.fromLab.test;

import com.fromLab.exception.BusinessException;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.OpenprojectURL;
import org.junit.Test;

/**
 * @author wsh
 * @date 2020-03-07
 */
public class GetCustomFieldNumUtilTest {

    @Test
    public void testGetCustomFieldNum() throws BusinessException {
        OpenprojectURL openprojectURL = new OpenprojectURL(
                "http://projects.plugininide.com/openproject" + OpenprojectURL.WORK_PACKAGES_URL,
                "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123");
        System.out.println(GetCustomFieldNumUtil.getCustomFieldNum("Task type", openprojectURL));

    }
}
