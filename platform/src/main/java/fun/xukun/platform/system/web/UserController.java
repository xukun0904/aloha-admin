package fun.xukun.platform.system.web;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.common.web.BaseController;
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;
import fun.xukun.platform.security.model.UserInfo;
import fun.xukun.platform.security.service.AuthService;
import fun.xukun.platform.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 日期:2020/6/22
 * 用户数据控制类
 *
 * @author xukun
 * @version 1.00
 */
@RestController
@RequestMapping("user")
@Api(value = "API - UserController", tags = "用户模块接口详情")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService service;

    private final AuthService authService;

    @GetMapping("list")
    @ApiOperation(value = "分页查询用户", notes = "列表查询,返回用户分页列表,支持GET方式", response = ResponseResult.class)
    public ResponseResult<PageResponse<UserExt>> list(UserQuery query, PageRequest pageRequest) {
        PageResponse<UserExt> pageResponse = this.service.list(query, pageRequest);
        return ResponseResultBuilder.builder().success(pageResponse);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "更新用户", notes = "更新用户,支持PUT方式", response = ResponseResult.class)
    @PutMapping
    public ResponseResult<Void> update(UserExt bean) {
        this.service.update(bean);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('user:add')")
    @ApiOperation(value = "新增用户", notes = "新增用户,支持POST方式", response = ResponseResult.class)
    @PostMapping
    public ResponseResult<Void> insert(UserExt bean) {
        this.service.insert(bean);
        return ResponseResultBuilder.builder().success();
    }

    @ApiOperation(value = "批量删除用户", notes = "批量删除用户,主键使用逗号分隔,支持DELETE方式", response = ResponseResult.class)
    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseResult<Void> multipleDelete(@PathVariable("ids") String ids) {
        this.service.multipleDelete(ids);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "更新用户部分信息", notes = "更新用户部分信息,支持PATCH方式", response = ResponseResult.class)
    @PatchMapping
    public ResponseResult<Void> updatePatch(User bean) {
        this.service.updatePatch(bean);
        return ResponseResultBuilder.builder().success();
    }

    @ApiOperation(value = "修改当前用户密码", notes = "修改当前用户密码,支持POST方式", response = ResponseResult.class)
    @PostMapping("password/update")
    public ResponseResult<Void> updatePassword(String oldPassword, String newPassword) {
        UserInfo user = authService.getCurrentUserInfo(request);
        service.updatePassword(user, oldPassword, newPassword);
        return ResponseResultBuilder.builder().success();
    }
}
