package com.snooker.domain.player;

/**
 * Authored by WangJunyu on 2017/4/8
 */
public class PlayerInfo {
    private String name;
    private String avatarUrl;
    private String country;
    private String birthday;
    private String turnedPro;
    private String rank;
    private String titles;
    private String largeTitles;
    private String microTitles;
    private String inviteTitles;
    private String centuryBreak;
    private String maxBreak;
    private String desc;

    public PlayerInfo(String name, String avatarUrl, String country, String birthday, String turnedPro,
                      String rank, String titles, String largeTitles, String microTitles,
                      String inviteTitles, String centuryBreak, String maxBreak, String desc) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.country = country.substring(country.indexOf("：")+1);
        this.birthday = birthday.substring(birthday.indexOf("：")+1);
        this.turnedPro = turnedPro.substring(turnedPro.indexOf("：")+1);
        this.desc = desc.substring(desc.indexOf("：")+1);
        this.rank = rank.substring(rank.indexOf("：")+1);
        this.titles = titles.replace("[详情]", "");
        this.largeTitles = largeTitles.replace("[详情]", "");
        this.microTitles = microTitles.replace("[详情]", "");
        this.inviteTitles = inviteTitles.replace("[详情]", "");
        this.centuryBreak = centuryBreak.replace("[详情]", "");
        this.maxBreak = maxBreak.replace("[详情]", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTurnedPro() {
        return turnedPro;
    }

    public void setTurnedPro(String turnedPro) {
        this.turnedPro = turnedPro;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getLargeTitles() {
        return largeTitles;
    }

    public void setLargeTitles(String largeTitles) {
        this.largeTitles = largeTitles;
    }

    public String getMicroTitles() {
        return microTitles;
    }

    public void setMicroTitles(String microTitles) {
        this.microTitles = microTitles;
    }

    public String getInviteTitles() {
        return inviteTitles;
    }

    public void setInviteTitles(String inviteTitles) {
        this.inviteTitles = inviteTitles;
    }

    public String getCenturyBreak() {
        return centuryBreak;
    }

    public void setCenturyBreak(String centuryBreak) {
        this.centuryBreak = centuryBreak;
    }

    public String getMaxBreak() {
        return maxBreak;
    }

    public void setMaxBreak(String maxBreak) {
        this.maxBreak = maxBreak;
    }
}
