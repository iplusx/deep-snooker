package com.snooker.domain.match;

import java.util.List;

/**
 * @author WangJunyu
 * @date 16/12/2
 * @e-mail iplusx@foxmail.com
 * @description
 */
public class Info {
    // 比赛名称
    private String name;
    // 比赛时间
    private String during;
    // 比赛性质
    private String type;
    // 比赛地点
    private String place;
    // 比赛场馆
    private String venue;
    // 比赛人数
    private String number;
    // 所属赛季
    private String season;
    // 卫冕冠军
    private String defendingChampion;
    // 本届冠军
    private String champion;
    // 本届破百
    private String centuryBreak;
    // 赞助商
    private String sponsor;
    // 总奖金
    private String totalPrize;
    // 奖金数组
    private List<String> prizes;
    // 单杆最高奖金
    private String highestBreakPrize;
    // 147满分奖金
    private String _147BreakPrize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getDefendingChampion() {
        return defendingChampion;
    }

    public void setDefendingChampion(String defendingChampion) {
        this.defendingChampion = defendingChampion;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getCenturyBreak() {
        return centuryBreak;
    }

    public void setCenturyBreak(String centuryBreak) {
        this.centuryBreak = centuryBreak;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public List<String> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<String> prizes) {
        this.prizes = prizes;
    }

    public String getHighestBreakPrize() {
        return highestBreakPrize;
    }

    public void setHighestBreakPrize(String highestBreakPrize) {
        this.highestBreakPrize = highestBreakPrize;
    }

    public String get_147BreakPrize() {
        return _147BreakPrize;
    }

    public void set_147BreakPrize(String _147BreakPrize) {
        this._147BreakPrize = _147BreakPrize;
    }
}
