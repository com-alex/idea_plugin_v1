package com.fromLab.test;

import com.fromLab.utils.DateUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wsh
 * @date 2019-12-27
 */
public class DateUtilsTest {

    @Test
    public void testDate2String(){
        Date currentTime = new Date();
        String result = DateUtils.date2String(currentTime);
        System.out.println(result);
    }

    @Test
    public void testString2Date(){
        String dateString = "2019-12-12 23:09:10";
        Date date = DateUtils.string2Date(dateString);
        System.out.println(date);
    }


    @Test
    public void testDate2LocalDateTime(){
        LocalDateTime localDateTime = DateUtils.date2LocalDateTime(new Date());
        System.out.println(localDateTime);
    }

    @Test
    public void testLocalDateTime2Date(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = DateUtils.localDateTime2Date(localDateTime);
        System.out.println(date);
    }

    @Test
    public void testPlusOneDay(){
        Date date = new Date();
        DateUtils.plusOneDay(date);
    }


}
