package fun.xukun.platform.system.web;

import fun.xukun.common.model.constant.Constants;
import fun.xukun.model.domain.system.Department;
import fun.xukun.model.domain.system.Menu;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.platform.system.service.DepartmentService;
import fun.xukun.platform.system.service.MenuService;
import fun.xukun.platform.system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 日期:2020/6/7
 * 系统管理页面类
 *
 * @author xukun
 * @version 1.00
 */
@ApiIgnore
@Controller
@RequestMapping(Constants.VIEW_PREFIX)
public class SystemPageController {

    private final MenuService menuService;

    private final DepartmentService departmentService;

    private final UserService userService;

    public SystemPageController(MenuService menuService, DepartmentService departmentService, UserService userService) {
        this.menuService = menuService;
        this.departmentService = departmentService;
        this.userService = userService;
    }

    @GetMapping("menu/manage")
    public String menuManagePage() {
        return "system/menu/manage";
    }

    @GetMapping("menu/add/{parentId}")
    public String menuAddPage(@PathVariable("parentId") String parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "system/menu/add";
    }

    @GetMapping("menu/update/{id}")
    public String menuUpdatePage(@PathVariable("id") String id, Model model) {
        Menu bean = menuService.getById(id);
        model.addAttribute("bean", bean);
        return "system/menu/update";
    }

    @GetMapping("role/manage")
    public String roleManagePage() {
        return "system/role/manage";
    }

    @GetMapping("department/manage")
    public String departmentManagePage() {
        return "system/department/manage";
    }

    @GetMapping("department/add/{parentId}")
    public String departmentAddPage(@PathVariable("parentId") String parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "system/department/add";
    }

    @GetMapping("department/update/{id}")
    public String departmentUpdatePage(@PathVariable("id") String id, Model model) {
        Department bean = departmentService.getById(id);
        model.addAttribute("bean", bean);
        return "system/department/update";
    }

    @GetMapping("user/manage")
    public String userManagePage() {
        return "system/user/manage";
    }

    @GetMapping("user/add")
    public String userAddPage() {
        return "system/user/add";
    }

    @GetMapping("user/update/{id}")
    public String userUpdatePage(@PathVariable("id") String id, Model model) {
        UserExt bean = userService.getById(id);
        model.addAttribute("bean", bean);
        return "system/user/update";
    }

    @GetMapping("user/view/{id}")
    public String userViewPage(@PathVariable("id") String id, Model model) {
        UserExt bean = userService.getById(id);
        model.addAttribute("bean", bean);
        return "system/user/view";
    }

    @GetMapping("password/update")
    public String passwordUpdatePage() {
        return "system/user/password_update";
    }
}
