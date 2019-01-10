package com.snooker.domain.match;

/**
 * @author WangJunyu
 * @date 16/11/28
 * @e-mail iplusx@foxmail.com
 * @description
 */
public class Against {
    //对阵序号
    private String no;
    private String p1name;
    private String p1set;
    private String p2name;
    private String p2set;
    private int grab;
    private String dzid;

    public Against(){}

    public Against(String no, String p1name, String p1set, String p2name, String p2set, int grab, String dzid) {
        this.no = no;
        this.p1name = p1name;
        this.p1set = p1set;
        this.p2name = p2name;
        this.p2set = p2set;
        this.grab = grab;
        this.dzid = dzid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getP1name() {
        return p1name;
    }

    public void setP1name(String p1name) {
        this.p1name = p1name;
    }

    public String getP1set() {
        return p1set;
    }

    public void setP1set(String p1set) {
        this.p1set = p1set;
    }

    public String getP2name() {
        return p2name;
    }

    public void setP2name(String p2name) {
        this.p2name = p2name;
    }

    public String getP2set() {
        return p2set;
    }

    public void setP2set(String p2set) {
        this.p2set = p2set;
    }

    public int getGrab() {
        return grab;
    }

    public void setGrab(int grab) {
        this.grab = grab;
    }

    public String getDzid() {
        return dzid;
    }

    public void setDzid(String dzid) {
        this.dzid = dzid;
    }

    public int getResult(String pickName) {
        if (pickName.equals(p1name)) {  //选了p1
            if (!p1set.equals("...") && !p2set.equals("...")) {
                if (Integer.parseInt(p1set) > Integer.parseInt(p2set) && Integer.parseInt(p1set) == this.grab) {
                    return 2;
                }
                else if (Integer.parseInt(p2set) > Integer.parseInt(p1set) && Integer.parseInt(p2set) == this.grab) {
                    return -1;
                }
            }
            else if (p1set.equals("w/o")) {
                return 2;
            }
            else if (p1set.equals("n/s") || p1set.equals("w/d")) {
                return -1;
            }
        }
        if (pickName.equals(p2name)){  //选了p2
            if (!p1set.equals("...") && !p2set.equals("...")) {
                if (Integer.parseInt(p2set) > Integer.parseInt(p1set) && Integer.parseInt(p2set) == this.grab) {
                    return 2;
                }
                else if (Integer.parseInt(p1set) > Integer.parseInt(p2set) && Integer.parseInt(p1set) == this.grab) {
                    return -1;
                }
            }
            else if (p2set.equals("w/o")) {
                return 2;
            }
            else if (p2set.equals("n/s") || p2set.equals("w/d")) {
                return -1;
            }
        }
        return 0;
    }
}
