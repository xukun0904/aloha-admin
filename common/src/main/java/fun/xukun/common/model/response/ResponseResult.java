package fun.xukun.common.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日期:2020/1/21
 * Restful响应值
 *
 * @author xukun
 * @version 1.00
 */
@Data
@NoArgsConstructor
public class ResponseResult<T> {
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

    /**
     * 返回数据
     */
    private T data;

    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.message = resultCode.message();
        this.code = resultCode.code();
    }

    public ResponseResult(ResultCode resultCode, T data) {
        this.success = resultCode.success();
        this.message = resultCode.message();
        this.code = resultCode.code();
        this.data = data;
    }

}
