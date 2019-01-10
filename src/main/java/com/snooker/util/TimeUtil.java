package com.snooker.util;

import com.snooker.exception.InnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author WangJunyu
 * @date 16/12/3
 * @e-mail iplusx@foxmail.com
 * @description 日期时间类工具
 */
@Component
public class TimeUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat YMD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat YMDHM_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public String getDateString(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    public Date stringToDate(String str, SimpleDateFormat format) throws InnerException {
        try {
            return format.parse(str);
        } catch (ParseException e) {
//            logger.error("time : {},error msg : {}", str, e.getMessage());
            throw new InnerException();
        }
    }

    /**
     * 获取距离今日指定天数的日期
     *
     * @param original 原日期
     * @param offset 偏移量,正数往后推,负数往前移动
     * @return
     */
    public Date getOffsetDate(Date original, int offset) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(original);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    /**
     * 获取两个日期相差的天数 尤其注意：相同日期认为是1
     *
     * @param before 前一个日期
     * @param after 后一个日期
     * @return 天数
     */
    public int getDays(Date before, Date after) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(after);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(before);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                {
                    timeDistance += 366;
                }
                else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day1-day2) + 1;
        }
        else //不同年
        {
            return day1-day2 + 1;
        }
    }
}
