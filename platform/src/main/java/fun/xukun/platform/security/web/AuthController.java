package fun.xukun.platform.security.web;

import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.common.util.CookieUtils;
import fun.xukun.common.web.BaseController;
import fun.xukun.model.domain.system.request.LoginRequest;
import fun.xukun.model.domain.system.response.AuthCode;
import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.security.jwt.JwtComponent;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.security.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 日期:2020/6/24
 * 鉴权据控制类
 *
 * @author xukun
 * @version 1.00
 */
@RestController
@RequestMapping("auth")
@Api(value = "API - LoginController", tags = "授权接口详情")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final CaptchaService captchaService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtComponent jwtComponent;

    private final AlohaProperties properties;

    @PostMapping("login")
    @ApiOperation(value = "登录接口", notes = "登录系统,支持POST方式", response = ResponseResult.class)
    public ResponseResult<Void> login(LoginRequest loginRequest) {
        // 校验验证码是否正确
        captchaService.check(request.getSession().getId(), loginRequest.getVerifyCode());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            ExceptionCast.cast(AuthCode.USER_INFO_INCORRECT);
        } catch (InternalAuthenticationServiceException e) {
            e.printStackTrace();
            ExceptionCast.cast(AuthCode.USER_LOCKED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        String token = jwtComponent.generateToken(userInfo);
        // 存储到cookie中
        CookieUtils.setCookie(request, response, properties.getJwt().getCookieName(), token, properties.getJwt().getCookieMaxAge(), "UTF-8", true);
        return ResponseResultBuilder.builder().success();
    }

    @GetMapping("captcha")
    @ApiOperation(value = "获取登录验证码", notes = "获取登录验证码,支持GET方式", response = ResponseResult.class)
    public void captcha() throws IOException {
        captchaService.generate(request, response);
    }
}
