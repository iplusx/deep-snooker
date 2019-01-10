package com.snooker.domain.player;

/**
 * Authored by WangJunyu on 2017/4/7
 */
public class Ranking {
    private String no;
    private String name;
    private String prize;
    private String uid;
    private String nationality;

    public Ranking() {
    }

    public Ranking(String no, String name, String prize, String uid, String nationality) {
        this.no = no;
        this.name = name;
        this.prize = prize;
        this.uid = uid;
        this.nationality = nationality;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
