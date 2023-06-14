package com.example.collect.util;

import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class DateUtil {
    // 标准日期格式
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // 时间戳格式
    private static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取标准当前时间戳格式
     * @return
     */
    public static String getCurrentTime(){
        return timeStampFormat.format(new Date());
    }
    /**
     * 查看当前时间是否超过测试任务截止日期
     * @param date
     * @return
     * @throws ParseException
     */
    public boolean isOverDue(String date) throws ParseException {
        Date dueDate = dateFormat.parse(date);
        return new Date().after(dueDate);
    }
}
