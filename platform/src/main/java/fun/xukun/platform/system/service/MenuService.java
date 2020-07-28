package fun.xukun.platform.system.service;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.EleTree;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.model.domain.system.Menu;

import java.util.List;

/**
 * 日期:2020/6/5
 * 菜单服务接口
 *
 * @author xukun
 * @version 1.00
 */
public interface MenuService {

    /**
     * 获取EleTree
     *
     * @return EleTree
     */
    EleTree<Menu> getMenuTree();

    /**
     * 更新菜单
     *
     * @param menu 菜单
     */
    void update(Menu menu);

    /**
     * 新增菜单
     *
     * @param menu 菜单
     */
    void insert(Menu menu);

    /**
     * 批量删除菜单
     *
     * @param ids 主键字符
     */
    void multipleDelete(String ids);

    /**
     * 分页查询菜单
     *
     * @param menu        查询参数
     * @param pageRequest 分页参数
     * @return 分页结果
     */
    PageResponse<Menu> listMenus(Menu menu, PageRequest pageRequest);

    /**
     * 根据主键获取菜单
     *
     * @param id 主键
     * @return 菜单
     */
    Menu getById(String id);

    /**
     * 查询EleTree集合
     *
     * @param userId 用户主键
     * @return EleTree集合
     */
    List<EleTree<Menu>> listMenuTreeByUserId(String userId);

    /**
     * 查询EleTree集合
     *
     * @return EleTree集合
     */
    List<EleTree<Menu>> listMenuTree();
}
