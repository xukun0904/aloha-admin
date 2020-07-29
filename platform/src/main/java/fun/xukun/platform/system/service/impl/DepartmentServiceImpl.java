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
import fun.xukun.common.model.response.SelectTree;
import fun.xukun.common.util.CollectionUtils;
import fun.xukun.common.util.PageUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.system.Department;
import fun.xukun.model.manager.DepartmentManager;
import fun.xukun.platform.system.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日期:2020/6/5
 * 部门服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentManager departmentManager;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<EleTree<Department>> listEleTree() {
        List<Department> list = getDepartments();
        // 转化成EleTree格式
        List<EleTree<Department>> trees = convertDepartments(list);
        // 封装成树形集合
        return getDepartmentTrees(trees);
    }

    private List<Department> getDepartments() {
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Department::getStatus, Constants.DEPARTMENT_STATUS_ENABLE);
        queryWrapper.orderByAsc(Department::getOrderNumber);
        queryWrapper.orderByDesc(Department::getId);
        return departmentManager.list(queryWrapper);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<SelectTree<Department>> listSelectTree() {
        List<Department> list = getDepartments();
        // 转化成SelectTree格式
        List<SelectTree<Department>> trees = convertSelectTree(list);
        return getSelectTrees(trees);
    }

    private List<SelectTree<Department>> getSelectTrees(List<SelectTree<Department>> nodes) {
        if (nodes == null) {
            return null;
        }
        // 第一层节点
        List<SelectTree<Department>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || Constants.TOP_NODE_ID.equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (SelectTree<Department> parent : nodes) {
                String id = parent.getId();
                // 匹配子节点，进行封装
                if (id != null && id.equals(pid)) {
                    List<SelectTree<Department>> childs = parent.getChildren();
                    if (CollectionUtils.isEmpty(childs)) {
                        childs = new ArrayList<>();
                        parent.setChildren(childs);
                    }
                    childs.add(children);
                    return;
                }
            }
        });
        return topNodes;
    }

    private List<SelectTree<Department>> convertSelectTree(List<Department> departments) {
        List<SelectTree<Department>> trees = new ArrayList<>();
        departments.forEach(department -> {
            SelectTree<Department> tree = new SelectTree<>();
            tree.setId(String.valueOf(department.getId()));
            tree.setName(department.getName());
            tree.setParentId(department.getParentId());
            trees.add(tree);
        });
        return trees;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public EleTree<Department> getEleTree() {
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Department::getOrderNumber);
        queryWrapper.orderByDesc(Department::getId);
        List<Department> list = departmentManager.list(queryWrapper);
        // 转化成EleTree格式
        List<EleTree<Department>> trees = convertDepartments(list);
        // 封装成树形
        return buildDepartmentTree(trees);
    }

    @Override
    public void update(Department department) {
        // 设置更新时间
        department.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.departmentManager.updateById(department);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }

    @Override
    public void insert(Department department) {
        department.setId(null);
        department.setCreateTime(LocalDateTime.now());
        boolean isSuccess = this.departmentManager.save(department);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.SAVE_FAIL);
        }
    }

    @Override
    public void multipleDelete(String ids) {
        List<String> idList = StringUtils.split(ids, StringPool.COMMA);
        // 删除部门
        this.departmentManager.removeByIds(idList);
        // 递归删除子部门
        this.delete(idList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public PageResponse<Department> list(Department department, PageRequest pageRequest) {
        // 分页查询，默认根据主键倒序
        IPage<Department> page = PageUtils.getPage(pageRequest);
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        if (department != null) {
            if (department.getParentId() != null) {
                // 过滤父主键
                queryWrapper.eq(Department::getParentId, department.getParentId());
            }
            if (StringUtils.isNotBlank(department.getName())) {
                // 模糊匹配部门名
                queryWrapper.like(Department::getName, department.getName());
            }
        }
        IPage<Department> rolePage = departmentManager.page(page, queryWrapper);
        // 转化成table需要格式
        return PageUtils.convertPageResponse(rolePage);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public Department getById(String id) {
        return departmentManager.getById(id);
    }

    @Override
    public void updatePatch(Department bean) {
        // 设置更新时间
        bean.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = this.departmentManager.updateById(bean);
        if (!isSuccess) {
            ExceptionCast.cast(CommonCode.UPDATE_FAIL);
        }
    }

    private void delete(List<String> idList) {
        LambdaQueryWrapper<Department> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Department::getParentId, idList);
        // 查询所有子节点
        List<Department> list = this.departmentManager.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> childrenIdList = list.stream().map(Department::getId).collect(Collectors.toList());
            // 不为空则删除
            this.departmentManager.removeByIds(childrenIdList);
            // 递归操作
            this.delete(childrenIdList);
        }
    }

    private List<EleTree<Department>> convertDepartments(List<Department> departments) {
        List<EleTree<Department>> trees = new ArrayList<>();
        departments.forEach(department -> {
            EleTree<Department> tree = new EleTree<>();
            tree.setId(String.valueOf(department.getId()));
            tree.setLabel(department.getName());
            tree.setData(department);
            tree.setParentId(department.getParentId());
            trees.add(tree);
        });
        return trees;
    }

    private EleTree<Department> buildDepartmentTree(List<EleTree<Department>> nodes) {
        List<EleTree<Department>> topNodes = getDepartmentTrees(nodes);
        if (topNodes == null) {
            return null;
        }
        EleTree<Department> root = new EleTree<>();
        root.setId(Constants.TOP_NODE_ID);
        // 设置顶级名称
        root.setLabel(Constants.DEPARTMENT_TOP_NODE_NAME);
        root.setLeaf(true);
        root.setChildren(topNodes);
        return root;
    }

    private List<EleTree<Department>> getDepartmentTrees(List<EleTree<Department>> nodes) {
        if (nodes == null) {
            return null;
        }
        // 第一层节点
        List<EleTree<Department>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || Constants.TOP_NODE_ID.equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (EleTree<Department> parent : nodes) {
                String id = parent.getId();
                // 匹配子节点，进行封装
                if (id != null && id.equals(pid)) {
                    List<EleTree<Department>> childs = parent.getChildren();
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
