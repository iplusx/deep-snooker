package com.snooker.domain.player;

/**
 * Authored by WangJunyu on 2017/4/10
 *
 * 球员对阵数据
 */
public class PlayerAgainstData {
    private String name;
    private String avatarUrl;
    private String pkWinRatio;
    private String pkWinTimes;
    private String match;
    private String title;
    private String session;
    private String set;
    private String century;
    private String maximum;
    private String kill;
    private String decider;
    private String sessionRatio;
    private String setRatio;
    private String centuryRatio;
    private String deciderRatio;
    private String uid;

    public PlayerAgainstData(String name, String pkWinRatio, String pkWinTimes, String match, String title, String session,
                             String set, String century, String maximum, String kill, String decider, String sessionRatio,
                             String setRatio, String centuryRatio, String deciderRatio, String uid, String avatarUrl) {
        this.name = name;
        this.pkWinRatio = pkWinRatio;
        this.pkWinTimes = pkWinTimes;
        this.match = match;
        this.title = title;
        this.session = session;
        this.set = set;
        this.century = century;
        this.maximum = maximum;
        this.kill = kill;
        this.decider = decider;
        this.sessionRatio = sessionRatio;
        this.setRatio = setRatio;
        this.centuryRatio = centuryRatio;
        this.deciderRatio = deciderRatio;
        this.uid = uid;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkWinRatio() {
        return pkWinRatio;
    }

    public void setPkWinRatio(String pkWinRatio) {
        this.pkWinRatio = pkWinRatio;
    }

    public String getPkWinTimes() {
        return pkWinTimes;
    }

    public void setPkWinTimes(String pkWinTimes) {
        this.pkWinTimes = pkWinTimes;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getCentury() {
        return century;
    }

    public void setCentury(String century) {
        this.century = century;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
    }

    public String getDecider() {
        return decider;
    }

    public void setDecider(String decider) {
        this.decider = decider;
    }

    public String getSessionRatio() {
        return sessionRatio;
    }

    public void setSessionRatio(String sessionRatio) {
        this.sessionRatio = sessionRatio;
    }

    public String getSetRatio() {
        return setRatio;
    }

    public void setSetRatio(String setRatio) {
        this.setRatio = setRatio;
    }

    public String getCenturyRatio() {
        return centuryRatio;
    }

    public void setCenturyRatio(String centuryRatio) {
        this.centuryRatio = centuryRatio;
    }

    public String getDeciderRatio() {
        return deciderRatio;
    }

    public void setDeciderRatio(String deciderRatio) {
        this.deciderRatio = deciderRatio;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
