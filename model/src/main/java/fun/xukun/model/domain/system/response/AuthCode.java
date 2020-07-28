package fun.xukun.model.domain.system.response;

import fun.xukun.common.model.response.ResultCode;

/**
 * 日期:2020/6/28
 *
 * @author xukun
 * @version 1.00
 */
public enum AuthCode implements ResultCode {
    LOGIN_FAIL(false, 20001, "登录失败！"),
    CAPTCHA_NOT_EXIST(false, 20002, "请输入验证码！"),
    CAPTCHA_EXPIRED(false, 20003, "验证码已过期！"),
    CAPTCHA_INCORRECT(false, 20004, "验证码不正确！"),
    USER_INFO_INCORRECT(false, 20005, "用户名或密码错误！"),
    USER_LOCKED(false, 20006, "用户已被锁定！"),
    OLD_PASSWORD_INCORRECT(false, 20007, "原密码不正确！"),
    ;

    /**
     * 操作是否成功
     */
    private boolean success;

    /**
     * 操作代码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    AuthCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
