package com.nft.entity;

/**
 * 公共的返回码
 *    返回码code：
 *      成功：10000
 *      失败：10001
 *      未登录：10002
 *      未授权：10003
 *      抛出异常：99999
 */
public enum ResultCode {
    SUCCESS(true,10000,"操作成功"),
    //---系统错误返回码-----
    FAIL(false,10001,"操作失败"),
    UNAUTHENTICATED(false,10002,"您还未登录"),
    UNAUTHORISE(false,10003,"请使用管理员身份登录"),
    ACCOUNT_LOCK_EXCEPTION(false,10004,"账号已被锁定，请联系管理员"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),

    //---用户操作返回码  2xxxx----
    USERNAME_PASSWORD_ERROR(false,20001,"用户名或密码错误"),
    UNKONWN_ACCOUNT_ERROR(false,20002,"用户名不存在"),
    INCORRECT_CREDENTAIL_ERROR(false,20003,"密码不正确"),
    REPEAT_USER_ACCOUNTERROR(false,20004,"用户名已存在"),
    //---用户操作返回码  3xxxx----
    REPEAT_INPUT_ERROR(false,30001,"输入内容已存在"),
    PARAMETER_NULL_ERROR(false,30002,"输入内容为空"),
    //---权限操作返回码----
    //---其他操作返回码----
    EMPTY_CONTENT(true,50001,"暂无内容，看看其他的吧"),
    INSUFFICIENT_BALANCE(false,50002,"账户余额不足，请先充值"),
    NOT_SET_SELL(false,50003,"该NFT未设置出售"),
    WRONG_BANK_CARD_NUMBER(false,50004,"银行卡号格式不正确"),
    NOT_ADD_TO_CART(false,50005,"无法添加自己出售的商品到购物车");


    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
