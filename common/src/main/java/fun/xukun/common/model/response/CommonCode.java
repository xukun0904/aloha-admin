package fun.xukun.common.model.response;

import lombok.ToString;

/**
 * 日期:2020/1/21
 * 通用响应码
 *
 * @author xukun
 * @version 1.00
 */
@ToString
public enum CommonCode implements ResultCode {
    SUCCESS(true, 200, "操作成功！"),
    NOT_FOUND(false, 404, "没有找到数据！"),
    UNAUTHENTICATED(false, 401, "此操作需要登陆系统！"),
    UNAUTHORIZED(false, 403, "权限不足，无权操作！"),
    METHOD_NOT_SUPPORTED(false, 405, "请求方式不支持！"),
    SERVER_ERROR(false, 500, "抱歉，系统繁忙，请稍后重试！"),
    UPDATE_FAIL(false, 10000, "更新失败！"),
    SAVE_FAIL(false, 10001, "保存失败！"),
    PARAMETER_ERROR(false, 10002, "参数错误！"),
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

    CommonCode(boolean success, int code, String message) {
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
