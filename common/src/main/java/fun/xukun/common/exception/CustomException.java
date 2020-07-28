package fun.xukun.common.exception;


import fun.xukun.common.model.response.ResultCode;

/**
 * 日期:2020/1/12
 * 描述:自定义异常类型
 *
 * @author xukun
 * @version 1.00
 */
public class CustomException extends RuntimeException {
    /**
     * 错误代码
     */
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
