package fun.xukun.model.domain.system.request;

import fun.xukun.common.model.request.RequestData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日期:2020/6/29
 *
 * @author xukun
 * @version 1.00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequest extends RequestData {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;
}
