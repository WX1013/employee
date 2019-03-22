package com.emp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/3/20 14:29
 */
public class CommonUtil {

    public static String date2String(Date date,String formart){
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String format = sdf.format(date);
        return format;
    }

}
