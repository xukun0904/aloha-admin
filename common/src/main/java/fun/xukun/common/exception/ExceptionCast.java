package fun.xukun.common.exception;


import fun.xukun.common.model.response.ResultCode;

/**
 * 日期:2020/1/12
 * 描述:统一异常抛出类
 *
 * @author xukun
 * @version 1.00
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
