package fun.xukun.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.constant.Constants;
import fun.xukun.common.model.constant.StringPool;
import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.CommonCode;
import fun.xukun.common.model.response.EleTree;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.util.CollectionUtils;
import fun.xukun.common.util.PageUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.system.Menu;
import fun.xukun.platform.system.manager.MenuManager;
import fun.xukun.platform.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日期:2020/6/5
 * 菜单服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    private final MenuManager menuManager;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<EleTree<Menu>> listMenuTreeByUserId(String userId) {
        // 根据用户主键查询菜单
        List<Menu> menuList = menuManager.listMenuTreeByUserId(userId);
        // 转化成EleTree格式
        List<EleTree<Menu>> menuTrees = convertMenus(menuList);
        // 封装成树形列表
        return getMenuTrees(menuTrees);
    }

    @Cacheable(cacheNames = "menu_cache", key = "'eletrees'")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<EleTree<Menu>> listMenuTree() {
        List<Menu> menuList = getMenus();
        // 转化成EleTree格式
        List<EleTree<Menu>> menuTrees = convertMenus(menuList);
        // 封装成树形列表
        return getMenuTrees(menuTrees);
    }

    private List<Menu> getMenus() {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Menu::getOrderNumber);
        queryWrapper.orderByDesc(Menu::getId);
        return menuManager.list(queryWrapper);
    }

    @Cacheable(cacheNames = "menu_cache", key = "'eletree'")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public EleTree<Menu> getMenuTree() {
        List<Menu> menuList = getMenus();
        // 转化成EleTree格式
        List<EleTree<Menu>> menuTrees = convertMenus(menuList);
        // 封装成树形
        return buildMenuTree(menuTrees);
    }

    @CacheEvict(cacheNames = "menu_cache", allEntries = true, beforeInvocation = true)
    @Override
    public void update(Menu menu) {
        // 设置更新时间
        menu.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.menuManager.updateById(menu);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }

    @CacheEvict(cacheNames = "menu_cache", allEntries = true, beforeInvocation = true)
    @Override
    public void insert(Menu menu) {
        menu.setId(null);
        menu.setCreateTime(LocalDateTime.now());
        boolean isSuccess = this.menuManager.save(menu);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.SAVE_FAIL);
        }
    }

    @CacheEvict(cacheNames = "menu_cache", allEntries = true, beforeInvocation = true)
    @Override
    public void multipleDelete(String ids) {
        List<String> idList = StringUtils.split(ids, StringPool.COMMA);
        // 删除菜单
        this.menuManager.removeByIds(idList);
        // 递归删除子菜单
        this.delete(idList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public PageResponse<Menu> listMenus(Menu menu, PageRequest pageRequest) {
        // 分页查询，默认根据排序字段排序
        IPage<Menu> page = PageUtils.getPage(pageRequest);
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        if (menu != null) {
            if (menu.getParentId() != null) {
                // 过滤父主键
                queryWrapper.eq(Menu::getParentId, menu.getParentId());
            }
            if (StringUtils.isNotBlank(menu.getName())) {
                // 模糊匹配菜单名
                queryWrapper.like(Menu::getName, menu.getName());
            }
        }
        IPage<Menu> rolePage = menuManager.page(page, queryWrapper);
        // 转化成table需要格式
        return PageUtils.convertPageResponse(rolePage);
    }

    @Cacheable(cacheNames = "menu_cache", key = "'menu_' + #id")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public Menu getById(String id) {
        return menuManager.getById(id);
    }

    private void delete(List<String> idList) {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Menu::getParentId, idList);
        // 查询所有子菜单
        List<Menu> list = this.menuManager.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> childrenIdList = list.stream().map(Menu::getId).collect(Collectors.toList());
            // 不为空则删除
            this.menuManager.removeByIds(childrenIdList);
            // 递归操作
            this.delete(childrenIdList);
        }
    }

    private List<EleTree<Menu>> convertMenus(List<Menu> menus) {
        List<EleTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            EleTree<Menu> tree = new EleTree<>();
            tree.setId(String.valueOf(menu.getId()));
            tree.setLabel(menu.getName());
            tree.setData(menu);
            tree.setParentId(menu.getParentId());
            trees.add(tree);
        });
        return trees;
    }

    private EleTree<Menu> buildMenuTree(List<EleTree<Menu>> nodes) {
        List<EleTree<Menu>> topNodes = getMenuTrees(nodes);
        if (topNodes == null) {
            return null;
        }
        EleTree<Menu> root = new EleTree<>();
        root.setId(Constants.TOP_NODE_ID);
        // 设置顶级名称
        root.setLabel(Constants.MENU_TOP_NODE_NAME);
        root.setLeaf(true);
        root.setChildren(topNodes);
        return root;
    }

    private List<EleTree<Menu>> getMenuTrees(List<EleTree<Menu>> nodes) {
        if (nodes == null) {
            return null;
        }
        // 第一层菜单
        List<EleTree<Menu>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || Constants.TOP_NODE_ID.equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (EleTree<Menu> parent : nodes) {
                String id = parent.getId();
                // 匹配子菜单，进行封装
                if (id != null && id.equals(pid)) {
                    List<EleTree<Menu>> childs = parent.getChildren();
                    if (CollectionUtils.isEmpty(childs)) {
                        childs = new ArrayList<>();
                        parent.setChildren(childs);
                    }
                    childs.add(children);
                    children.setLeaf(false);
                    parent.setLeaf(true);
                    return;
                }
            }
        });
        return topNodes;
    }
}
