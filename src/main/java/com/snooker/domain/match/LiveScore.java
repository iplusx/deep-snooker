package com.snooker.domain.match;

/**
 * @author WangJunyu
 * @date 16/11/2
 * @e-mail iplusx@foxmail.com
 * @description 比分直播
 */
public class LiveScore {
    //选手1
    private ScorePanelPlayer player1;
    //选手2
    private ScorePanelPlayer player2;
    //公共部分
    private ScorePanelCommon common;

    public LiveScore() {

    }

    public LiveScore(ScorePanelPlayer player1, ScorePanelPlayer player2, ScorePanelCommon common) {
        this.player1 = player1;
        this.player2 = player2;
        this.common = common;
    }

    public ScorePanelPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(ScorePanelPlayer player1) {
        this.player1 = player1;
    }

    public ScorePanelPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(ScorePanelPlayer player2) {
        this.player2 = player2;
    }

    public ScorePanelCommon getCommon() {
        return common;
    }

    public void setCommon(ScorePanelCommon common) {
        this.common = common;
    }
}
