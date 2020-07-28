package fun.xukun.platform.common.web;

import fun.xukun.common.model.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 日期:2020/6/7
 *
 * @author xukun
 * @version 1.00
 */
@ApiIgnore
@Controller
@RequestMapping(Constants.VIEW_PREFIX)
public class CommonPageController {

    @GetMapping("index")
    public String index() {
        return "common/index";
    }

    @GetMapping("icon")
    public String icon() {
        return "common/icon";
    }

    @GetMapping("layout")
    public String layout() {
        return "common/layout";
    }

    @GetMapping("404")
    public String error404() {
        return "common/404";
    }

    @GetMapping("401")
    public String error401() {
        return "common/401";
    }

    @GetMapping("403")
    public String error403() {
        return "common/403";
    }

    @GetMapping("500")
    public String error500() {
        return "common/500";
    }
}
