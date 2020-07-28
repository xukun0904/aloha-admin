package fun.xukun.common.exception;


import com.google.common.collect.ImmutableMap;
import fun.xukun.common.model.response.CommonCode;
import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 日期:2020/1/12
 * 描述:统一异常捕获类
 *
 * @author xukun
 * @version 1.00
 */
@Slf4j
@RestControllerAdvice
public class ExceptionCatch {

    /**
     * 定义map，配置异常类型所对应的错误代码
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    /**
     * 定义map的builder对象，去构建ImmutableMap
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    /**
     * 捕获CustomException此类异常
     *
     * @param customException 自定义异常
     * @return 响应前台数据
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult<Void> customException(CustomException customException) {
        customException.printStackTrace();
        ResultCode resultCode = customException.getResultCode();
        // 记录日志
        log.error("catch exception:{}", resultCode.message());
        return ResponseResultBuilder.builder().withCode(resultCode).build();
    }

    /**
     * 捕获Exception此类异常
     *
     * @param exception 异常
     * @return 响应前台数据
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> exception(Exception exception) {
        exception.printStackTrace();
        if (EXCEPTIONS == null) {
            // EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }
        // 从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        resultCode = resultCode == null ? CommonCode.SERVER_ERROR : resultCode;
        // 记录日志
        log.error("catch exception:{}", resultCode.message());
        return ResponseResultBuilder.builder().withCode(resultCode).build();
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult<Void> handleValidException(BindException exception) {
        exception.printStackTrace();
        // 记录日志
        log.error("catch exception:{}", exception.getMessage());
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        String message = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return ResponseResultBuilder.builder().withCode(CommonCode.PARAMETER_ERROR.code()).withSuccess(CommonCode.PARAMETER_ERROR.success()).withMessage(message).build();
    }

    static {
        // 定义异常类型所对应的错误代码
        builder.put(HttpRequestMethodNotSupportedException.class, CommonCode.METHOD_NOT_SUPPORTED);
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORIZED);
    }
}
