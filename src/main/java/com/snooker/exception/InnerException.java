package com.snooker.exception;

/**
 * @author WangJunyu
 * @date 16/11/3
 * @e-mail iplusx@foxmail.com
 * @description 内部异常
 */
public class InnerException extends RuntimeException {
    private static final long serialVersionUID = 6873040494095159124L;
    private Integer errcode;

    //异常代码定义
    //缺省异常
    public final static int DEFAULT_ERROR = 10000;

    //爬虫连接错误
    public static final int SCRAWLER_CONNECT_ERROR = 20001;
    //日期处理错误
    public static final int DATE_HANDLE_ERROR = 20002;

    //用户
    //用户不存在
    public static final int USER_NOT_FOUND = 30001;
    public static final int LOGIN_ERROR = 30002;
    public static final int SUGGESTION_OVERFLOW = 30003;
    public static final int SUG_CONTENT_OVERFLOW = 30004;

    //活动
    //已参加
    public static final int ALREADY_JOINED = 40001;
    //已发布该期活动
    public static final int ALREADY_PUBLISHED = 40002;
    //不存在
    public static final int NOT_EXIST = 40003;
    //活动已结束
    public static final int ALREADY_OVER = 40004;
    //未开始
    public static final int NOT_BEGIN = 40005;
    //玩家被淘汰
    public static final int GAME_OVER = 40006;
    //未到竞猜时间
//    public static final int TIME_BAD = 40007;
    //弃权
    public static final int ABSTAINED = 40008;
    //作弊
    public static final int CHEAT = 40010;

    //幸存者
    //当天赛程还未结束
    public static final int NOT_END = 41001;
    //明天的赛程还未出来
    public static final int NOT_READY = 41002;
    //无选择出局
    public static final int NO_PICK = 41003;

    //竞猜
    //比赛已经开始
    public static final  int MATCH_BEGINED = 42001;

    //每日签到
    public static final int ALREADY_SIGN_IN = 43001;

    //赛事
    //无赛事
    public static final int NO_MATCH = 50001;

    //交易
    //余额不足
    public static final int SCORE_LACK = 60001;
    public static final int PRIZE_OFF = 60002;
    public static final int ADDRESS_OVERFLOW = 60003;
    public static final int ADDRESS_DEFAULT_PROTECTED = 60004;
    public static final int ADDRESS_NOT_FOUND = 60005;
    public static final int PRIZE_NOT_EXIST = 60006;
    public static final int ORDER_NOT_EXIST = 60007;

    //微信
    public static final int ACCESS_TOKEN_FAIL = 70001;
    public static final int SEND_TEMPLATE_MSG_FAIL = 70002;

    //异常信息说明
    private static String getErrmsg(int code) {
        switch (code) {
            case DEFAULT_ERROR :
                return "服务器出了点问题";
            case ALREADY_SIGN_IN :
                return "今天已签过到";
            case MATCH_BEGINED :
                return "比赛已经开始";
            case ORDER_NOT_EXIST :
                return "订单不存在";
            case PRIZE_NOT_EXIST :
                return "奖品不存在";
            case SUG_CONTENT_OVERFLOW :
                return "字数超过限制";
            case SEND_TEMPLATE_MSG_FAIL :
                return "发送模版消息失败";
            case ACCESS_TOKEN_FAIL :
                return "获取微信凭证失败";
            case SUGGESTION_OVERFLOW :
                return "每天最多允许提交3次";
            case ADDRESS_NOT_FOUND :
                return "收获地址不存在";
            case ADDRESS_DEFAULT_PROTECTED :
                return "不能删除默认地址";
            case PRIZE_OFF :
                return "奖品数量不足";
            case ADDRESS_OVERFLOW :
                return "地址过多";
            case LOGIN_ERROR :
                return "登录失败";
            case SCORE_LACK :
                return "积分余额不足";
            case CHEAT :
                return "作弊可不好,少点套路~";
            case ABSTAINED :
                return "来晚了,下次再来哦~";
            case NO_PICK :
                return "NP";
            case DATE_HANDLE_ERROR :
                return "日期处理错误";
            case GAME_OVER :
                return "淘汰";
            case NOT_EXIST :
                return "活动不存在";
            case ALREADY_OVER :
                return "活动已结束";
            case NOT_BEGIN :
                return "活动未开始";
            case ALREADY_PUBLISHED :
                return "已发布该期活动";
            case NO_MATCH :
                return "没有比赛";
            case NOT_END :
                return "今天的赛程还未结束";
            case NOT_READY :
                return "明天的赛程还未公布";
            case ALREADY_JOINED :
                return "已参与";
            case USER_NOT_FOUND :
                return "用户不存在";
            case SCRAWLER_CONNECT_ERROR :
                return "网络有点差";
            default:
                return "服务器内部错误";
        }
    }


    public InnerException() {
        super(getErrmsg(DEFAULT_ERROR));
        this.errcode = DEFAULT_ERROR;
    }

    public InnerException(String errmsg) {
        super(errmsg);
        this.errcode = DEFAULT_ERROR;
    }

    public InnerException(int code) {
        super(getErrmsg(code));
        this.errcode = code;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
}
