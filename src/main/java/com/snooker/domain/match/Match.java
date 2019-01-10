package com.snooker.domain.match;

/**
 * @author WangJunyu
 * @date 16/11/11
 * @e-mail iplusx@foxmail.com
 * @description 赛事领域
 */
public class Match implements Comparable{
    //标识
    private String id;
    //名称
    private String name;
    //时间
    private String time;
    // 海报url
    private String posterUrl;

    private String type;
    private String number;
    private byte status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    /**
     * 按时间降序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        Match match = (Match) o;
        int result = this.time.compareTo(match.getTime());
        if(result > 0) {
            return -1;
        }
        if(result < 0) {
            return 1;
        }
        if(result == 0) {
            return 0;
        }
        return -1;

    }
}
