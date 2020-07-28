package fun.xukun.common.model.response;

/**
 * 日期:2020/3/30
 * 响应值Builder类
 *
 * @author xukun
 * @version 1.00
 */
public final class ResponseResultBuilder {
    private boolean success;
    private int code;
    private String message;

    private ResponseResultBuilder() {
    }

    public static ResponseResultBuilder builder() {
        return new ResponseResultBuilder();
    }

    public ResponseResultBuilder withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ResponseResultBuilder withCode(int code) {
        this.code = code;
        return this;
    }

    public ResponseResultBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseResultBuilder withCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.success = resultCode.success();
        this.message = resultCode.message();
        return this;
    }

    public <T> ResponseResult<T> success() {
        return withCode(CommonCode.SUCCESS).build();
    }

    public <T> ResponseResult<T> success(T data) {
        return withCode(CommonCode.SUCCESS).build(data);
    }

    public <T> ResponseResult<T> build(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(success);
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(data);
        return responseResult;
    }

    public <T> ResponseResult<T> build() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(success);
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }
}
