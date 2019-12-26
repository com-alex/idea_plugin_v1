package com.fromLab.test;

import org.junit.Test;

/**
 * @author wsh
 * @date 2019-12-26
 */
public class TimeTest  {

    @Test
    public void testTime(){
        Long start = 1577352050902L;
        Long end = 1577352058005L;

        Integer min = (int)(end - start);
        System.out.println(min);
    }
}
