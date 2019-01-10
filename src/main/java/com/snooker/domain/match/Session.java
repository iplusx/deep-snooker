package com.snooker.domain.match;

import java.util.List;

/**
 * @author WangJunyu
 * @date 16/11/28
 * @e-mail iplusx@foxmail.com
 * @description
 */
public class Session {
    //比赛时间(HH:mm)
    private String time;
    //比赛轮次
    private String round;

    private List<Against> againstList;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public List<Against> getAgainstList() {
        return againstList;
    }

    public void setAgainstList(List<Against> againstList) {
        this.againstList = againstList;
    }
}
