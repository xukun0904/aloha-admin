package fun.xukun.platform.system.web;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.*;
import fun.xukun.common.web.BaseController;
import fun.xukun.model.domain.system.Department;
import fun.xukun.platform.system.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2020/6/19
 * 部门数据控制类
 *
 * @author xukun
 * @version 1.00
 */
@RestController
@RequestMapping("department")
@Api(value = "API - DepartmentController", tags = "部门模块接口详情")
public class DepartmentController extends BaseController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("trees")
    @ApiOperation(value = "查询EleTree部门集合", notes = "列表查询,返回启用的EleTree部门集合,支持GET方式", response = ResponseResult.class)
    public ResponseResult<List<EleTree<Department>>> listTree() {
        List<EleTree<Department>> menuTrees = this.service.listEleTree();
        return ResponseResultBuilder.builder().success(menuTrees);
    }

    @GetMapping("select/trees")
    @ApiOperation(value = "查询SelectTree部门集合", notes = "列表查询,返回启用的SelectTree部门集合,支持GET方式", response = ResponseResult.class)
    public List<SelectTree<Department>> listSelectTree() {
        return this.service.listSelectTree();
    }

    @GetMapping("tree")
    @ApiOperation(value = "查询EleTree部门集合", notes = "列表查询,返回有顶级节点的EleTree集合,支持GET方式", response = ResponseResult.class)
    public ResponseResult<List<EleTree<Department>>> listEleTree() {
        EleTree<Department> tree = this.service.getEleTree();
        // EleTree需要接受集合格式
        List<EleTree<Department>> trees = new ArrayList<>();
        trees.add(tree);
        return ResponseResultBuilder.builder().success(trees);
    }

    @GetMapping("list")
    @ApiOperation(value = "分页查询部门", notes = "列表查询,返回部门分页列表,支持GET方式", response = ResponseResult.class)
    public ResponseResult<PageResponse<Department>> list(Department bean, PageRequest pageRequest) {
        PageResponse<Department> pageResponse = this.service.list(bean, pageRequest);
        return ResponseResultBuilder.builder().success(pageResponse);
    }

    @PreAuthorize("hasAuthority('department:update')")
    @ApiOperation(value = "更新部门", notes = "更新部门,支持PUT方式", response = ResponseResult.class)
    @PutMapping
    public ResponseResult<Void> update(Department bean) {
        this.service.update(bean);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('department:add')")
    @ApiOperation(value = "新增部门", notes = "新增部门,支持POST方式", response = ResponseResult.class)
    @PostMapping
    public ResponseResult<Void> insert(Department bean) {
        this.service.insert(bean);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('department:delete')")
    @ApiOperation(value = "批量删除部门", notes = "批量删除部门,主键使用逗号分隔,支持DELETE方式", response = ResponseResult.class)
    @DeleteMapping("{ids}")
    public ResponseResult<Void> multipleDelete(@PathVariable("ids") String ids) {
        this.service.multipleDelete(ids);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('department:update')")
    @ApiOperation(value = "更新部门部分信息", notes = "更新部门部分信息,支持PATCH方式", response = ResponseResult.class)
    @PatchMapping
    public ResponseResult<Void> updatePatch(Department bean) {
        this.service.updatePatch(bean);
        return ResponseResultBuilder.builder().success();
    }
}
