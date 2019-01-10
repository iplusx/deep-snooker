package com.snooker.domain.match;

import java.util.List;

/**
 * @author WangJunyu
 * @date 16/11/28
 * @e-mail iplusx@foxmail.com
 * @description
 */
public class Schedule {
    //比赛日期(yyyy-MM-dd)
    private String date;
    //星期x
    private String day;

    private List<Session> sessionList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }
}
