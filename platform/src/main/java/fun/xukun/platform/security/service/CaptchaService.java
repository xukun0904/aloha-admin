package fun.xukun.platform.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 日期:2020/6/28
 * 验证码服务接口
 *
 * @author xukun
 * @version 1.00
 */
public interface CaptchaService {
    /**
     * 生成二维码
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException 异常
     */
    void generate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 校验二维码是否正确
     *
     * @param key        键
     * @param verifyCode 二维码
     */
    void check(String key, String verifyCode);
}
