package com.snooker.domain.match;

/**
 * @author WangJunyu
 * @date 16/11/2
 * @e-mail iplusx@foxmail.com
 * @description 比分面板的选手部分
 */
public class ScorePanelPlayer {
    //姓名
    private String name;
    //头像链接
    private String headImgUrl;
    //局分
    private String totalSetScore;
    //本局比分
    private String thisSetScore;
    //单杆得分
    private String breakScore;
    // 球员id
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getTotalSetScore() {
        return totalSetScore;
    }

    public void setTotalSetScore(String totalSetScore) {
        this.totalSetScore = totalSetScore;
    }

    public String getThisSetScore() {
        return thisSetScore;
    }

    public void setThisSetScore(String thisSetScore) {
        this.thisSetScore = thisSetScore;
    }

    public String getBreakScore() {
        return breakScore;
    }

    public void setBreakScore(String breakScore) {
        this.breakScore = breakScore;
    }
}
