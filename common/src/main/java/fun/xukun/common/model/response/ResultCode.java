package fun.xukun.common.model.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日期:2020/1/21
 * 响应码接口
 *
 * @author xukun
 * @version 1.00
 */
public interface ResultCode {
    /**
     * 操作是否成功,true为成功，false操作失败
     *
     * @return true或false
     */
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success();

    /**
     * 操作代码
     *
     * @return 响应码
     */
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code();

    /**
     * 提示信息
     *
     * @return 信息
     */
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message();
}
