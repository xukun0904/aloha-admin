package fun.xukun.platform.system.web;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.EleTree;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.model.response.ResponseResult;
import fun.xukun.common.model.response.ResponseResultBuilder;
import fun.xukun.common.web.BaseController;
import fun.xukun.model.domain.system.Menu;
import fun.xukun.platform.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2020/6/5
 * 菜单数据控制类
 *
 * @author xukun
 * @version 1.00
 */
@RestController
@RequestMapping("menu")
@Api(value = "API - MenuController", tags = "菜单模块接口详情")
public class MenuController extends BaseController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("trees")
    @ApiOperation(value = "查询EleTree菜单集合", notes = "列表查询,返回EleTree菜单集合支持GET方式", response = ResponseResult.class)
    public ResponseResult<List<EleTree<Menu>>> listMenuTree() {
        List<EleTree<Menu>> menuTrees = this.menuService.listMenuTree();
        return ResponseResultBuilder.builder().success(menuTrees);
    }

    @GetMapping("trees/{userId}")
    @ApiOperation(value = "查询EleTree菜单集合", notes = "列表查询,根据用户主键过滤,返回EleTree菜单集合支持GET方式", response = ResponseResult.class)
    public ResponseResult<List<EleTree<Menu>>> listMenuTreeByUserId(@PathVariable("userId") String userId) {
        List<EleTree<Menu>> menuTrees = this.menuService.listMenuTreeByUserId(userId);
        return ResponseResultBuilder.builder().success(menuTrees);
    }

    @GetMapping("tree")
    @ApiOperation(value = "查询EleTree菜单集合", notes = "列表查询,返回有顶级节点的EleTree菜单集合,支持GET方式", response = ResponseResult.class)
    public ResponseResult<List<EleTree<Menu>>> listManageMenuTree() {
        EleTree<Menu> tree = this.menuService.getMenuTree();
        // EleTree需要接受集合格式
        List<EleTree<Menu>> trees = new ArrayList<>();
        trees.add(tree);
        return ResponseResultBuilder.builder().success(trees);
    }

    @GetMapping("list")
    @ApiOperation(value = "分页查询菜单", notes = "列表查询,返回菜单分页列表,支持GET方式", response = ResponseResult.class)
    public ResponseResult<PageResponse<Menu>> listMenus(Menu menu, PageRequest pageRequest) {
        PageResponse<Menu> pageResponse = this.menuService.listMenus(menu, pageRequest);
        return ResponseResultBuilder.builder().success(pageResponse);
    }

    @PreAuthorize("hasAuthority('menu:update')")
    @ApiOperation(value = "更新菜单", notes = "更新菜单,支持PUT方式", response = ResponseResult.class)
    @PutMapping
    public ResponseResult<Void> update(Menu menu) {
        this.menuService.update(menu);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('menu:add')")
    @ApiOperation(value = "新增菜单", notes = "新增菜单,支持POST方式", response = ResponseResult.class)
    @PostMapping
    public ResponseResult<Void> insert(Menu menu) {
        this.menuService.insert(menu);
        return ResponseResultBuilder.builder().success();
    }

    @PreAuthorize("hasAuthority('menu:delete')")
    @ApiOperation(value = "批量删除菜单", notes = "批量删除菜单,主键使用逗号分隔,支持DELETE方式", response = ResponseResult.class)
    @DeleteMapping("{ids}")
    public ResponseResult<Void> multipleDelete(@PathVariable("ids") String ids) {
        this.menuService.multipleDelete(ids);
        return ResponseResultBuilder.builder().success();
    }
}
