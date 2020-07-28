package fun.xukun.platform.common.web;

import fun.xukun.common.model.constant.StringPool;
import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.platform.security.model.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 日期:2020/6/7
 *
 * @author xukun
 * @version 1.00
 */
@ApiIgnore
@Controller
public class IndexController {
    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        // 获取当前登录人信息
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userInfo);
        // 获取权限信息
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        String permissions = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(StringPool.COMMA));
        model.addAttribute("permissions", permissions);
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("401")
    public String error401() {
        return "error/401";
    }

    @GetMapping("403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("500")
    public String error500() {
        return "error/500";
    }

    /**
     * 获取当前用户权限
     */
    @GetMapping("authorities")
    @ResponseBody
    public ResponseResult<String> authorities() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        String permissions = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(StringPool.COMMA));
        return ResponseResultBuilder.builder().success(permissions);
    }
}
