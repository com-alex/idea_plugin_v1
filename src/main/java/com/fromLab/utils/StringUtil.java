package com.fromLab.utils;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public String addPlus(ArrayList<String> a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.size(); i++) {
            sb.append(a.get(i));
            if (i + 1 < a.size()) {
                sb.append(" ");
            }

        }
        return sb.toString();
    }

    public ArrayList<String> minusPlus(String str) {
        ArrayList<String> a = new ArrayList<>();
        String[] strs = str.split(" ");
        if (strs.length == 1) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            a.add(m.replaceAll("").trim());
        } else {
            for (int i = 0; i < strs.length; i++) {
                String temp = strs[i];
                if (StringUtils.isNumeric(temp)) {
                    a.add(temp);
                }
                if (i + 1 == strs.length) {
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(strs[i]);
                    a.add(m.replaceAll("").trim());
                }
            }
        }
        return a;
    }

    public boolean contains(ArrayList<String> a, String s) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(s)) {
                return true;
            }
        }
        return false;
    }
}
