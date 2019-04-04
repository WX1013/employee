package com.emp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/3/20 14:29
 */
public class CommonUtil {

    /**
     * 日期转换成字符串
     * @param date
     * @param formart
     * @return
     */
    public static String date2String(Date date,String formart){
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String format = sdf.format(date);
        return format;
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            return phone.matches(regex);
        }
    }
}
