package com.snooker.domain.player;

/**
 * Authored by WangJunyu on 2017/4/7
 */
public class FriPlayerRanking {
    private String name;
    private String uid;
    private String nationality;

    public FriPlayerRanking() {
    }

    public FriPlayerRanking(String name, String uid, String nationality) {
        this.name = name;
        this.uid = uid;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
