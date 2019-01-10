package com.snooker.domain.match;

/**
 * Author by WangJunyu on 17/2/12
 *
 * 对阵比分数据
 */
public class AgainstData {
    private String p150;
    private String p1score;
    private String set;
    private String p2score;
    private String p250;

    public AgainstData(String p150, String p1score, String set, String p2score, String p250) {
        this.p150 = p150;
        this.p1score = p1score;
        this.set = set;
        this.p2score = p2score;
        this.p250 = p250;
    }

    public String getP150() {
        return p150;
    }

    public void setP150(String p150) {
        this.p150 = p150;
    }

    public String getP1score() {
        return p1score;
    }

    public void setP1score(String p1score) {
        this.p1score = p1score;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getP2score() {
        return p2score;
    }

    public void setP2score(String p2score) {
        this.p2score = p2score;
    }

    public String getP250() {
        return p250;
    }

    public void setP250(String p250) {
        this.p250 = p250;
    }
}
