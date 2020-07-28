package fun.xukun.platform.security.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wf.captcha.SpecCaptcha;
import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.constant.Constants;
import fun.xukun.common.repository.RedisRepository;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.system.response.AuthCode;
import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.config.CaptchaProperties;
import fun.xukun.platform.security.service.CaptchaService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 日期:2020/6/28
 *
 * @author xukun
 * @version 1.00
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final AlohaProperties alohaProperties;

    private final RedisRepository redisRepository;

    public CaptchaServiceImpl(RedisRepository redisRepository, AlohaProperties alohaProperties) {
        this.redisRepository = redisRepository;
        this.alohaProperties = alohaProperties;
    }

    @Override
    public void generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        CaptchaProperties captchaProperties = alohaProperties.getCaptcha();
        // 三个参数分别为宽、高、位数
        SpecCaptcha captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLen());
        // 设置类型，纯数字、纯字母、字母数字混合
        captcha.setCharType(captchaProperties.getCharType());
        // 验证码存入session
        String key = request.getSession().getId();
        redisRepository.set(Constants.LOGIN_CAPTCHA_PREFIX + key, StringUtils.lowerCase(captcha.text()), captchaProperties.getTimeout());
        request.getSession().setAttribute("captcha", captcha.text().toLowerCase());
        // 输出图片流
        captcha.out(response.getOutputStream());
    }

    @Override
    public void check(String key, String verifyCode) {
        String codeInRedis = redisRepository.get(Constants.LOGIN_CAPTCHA_PREFIX + key, new TypeReference<String>() {
        });
        if (StringUtils.isBlank(verifyCode)) {
            ExceptionCast.cast(AuthCode.CAPTCHA_NOT_EXIST);
        }
        if (codeInRedis == null) {
            ExceptionCast.cast(AuthCode.CAPTCHA_EXPIRED);
        }
        if (!StringUtils.equalsIgnoreCase(verifyCode, codeInRedis)) {
            ExceptionCast.cast(AuthCode.CAPTCHA_INCORRECT);
        }
    }
}
