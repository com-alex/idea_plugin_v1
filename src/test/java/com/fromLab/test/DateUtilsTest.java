package com.fromLab.test;

import com.fromLab.utils.DateUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wsh
 * @date 2020-02-19
 */
public class DateUtilsTest {


    @Test
    public void testGetDate(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(now));

    }


    @Test
    public void testDate2String(){
        System.out.println(DateUtils.date2String(new Date()));
    }

    @Test
    public void testStirng2Date(){
        Date now = new Date();
        String beginDate = "2020-01-01";
        Date b = DateUtils.string2Date(beginDate);
        System.out.println(now.after(b));
    }
}
