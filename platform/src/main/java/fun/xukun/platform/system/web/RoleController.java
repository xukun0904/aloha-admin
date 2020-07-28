package fun.xukun.platform.system.web;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.domain.system.ext.RoleExt;
import fun.xukun.platform.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日期:2020/6/9
 * 角色数据控制类
 *
 * @author xukun
 * @version 1.00
 */
@RestController
@Api(value = "API - RoleController", tags = "角色模块接口详情")
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("page")
    @ApiOperation(value = "分页查询角色列表", notes = "分页查询,返回角色集合,支持GET方式", response = ResponseResult.class)
    public ResponseResult<PageResponse<RoleExt>> pageRoles(Role role, PageRequest pageRequest) {
        PageResponse<RoleExt> pageResponse = this.roleService.pageRoles(role, pageRequest);
        return ResponseResultBuilder.builder().success(pageResponse);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有角色列表", notes = "列表查询,返回角色集合,支持GET方式", response = ResponseResult.class)
    public ResponseResult<List<Role>> listRoles() {
        List<Role> roleList = this.roleService.listRoles();
        return ResponseResultBuilder.builder().success(roleList);
    }

    @PreAuthorize("hasAuthority('role:update')")
    @ApiOperation(value = "更新角色", notes = "更新角色,支持PUT方式", response = ResponseResult.class)
    @PutMapping
    public ResponseResult<Void> update(RoleExt role) {
        this.roleService.update(role);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('role:add')")
    @ApiOperation(value = "新增角色", notes = "新增角色,支持POST方式", response = ResponseResult.class)
    @PostMapping
    public ResponseResult<Void> insert(RoleExt role) {
        this.roleService.insert(role);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('role:delete')")
    @ApiOperation(value = "批量删除角色", notes = "批量删除菜单,主键使用逗号分隔,支持DELETE方式", response = ResponseResult.class)
    @DeleteMapping("{ids}")
    public ResponseResult<Void> multipleDelete(@PathVariable("ids") String ids) {
        this.roleService.multipleDelete(ids);
        return ResponseResultBuilder.builder().success();
    }
}
