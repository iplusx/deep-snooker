package com.snooker.domain.match;

/**
 * @author WangJunyu
 * @date 16/11/2
 * @e-mail iplusx@foxmail.com
 * @description 比分面板的公共部分
 */
public class ScorePanelCommon {
    //BO
    private String bestOf;
    //台面剩余
    private String leftScore;

    public String getBestOf() {
        return bestOf;
    }

    public void setBestOf(String bestOf) {
        this.bestOf = bestOf;
    }

    public String getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(String leftScore) {
        this.leftScore = leftScore;
    }
}
