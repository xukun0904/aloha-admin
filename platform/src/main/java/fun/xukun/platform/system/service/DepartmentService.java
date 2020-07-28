package fun.xukun.platform.system.service;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.EleTree;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.common.model.response.SelectTree;
import fun.xukun.model.domain.system.Department;

import java.util.List;

/**
 * 日期:2020/6/5
 * 部门服务接口
 *
 * @author xukun
 * @version 1.00
 */
public interface DepartmentService {

    /**
     * 获取EleTree
     *
     * @return EleTree
     */
    EleTree<Department> getEleTree();

    /**
     * 更新
     *
     * @param bean 实体类
     */
    void update(Department bean);

    /**
     * 新增
     *
     * @param bean 实体类
     */
    void insert(Department bean);

    /**
     * 批量删除
     *
     * @param ids 主键字符
     */
    void multipleDelete(String ids);

    /**
     * 分页查询
     *
     * @param bean    查询参数
     * @param request 分页参数
     * @return 分页结果
     */
    PageResponse<Department> list(Department bean, PageRequest request);

    /**
     * 根据主键获取
     *
     * @param id 主键
     * @return 实体类
     */
    Department getById(String id);

    /**
     * 更新部分信息
     *
     * @param bean 实体类
     */
    void updatePatch(Department bean);

    /**
     * 获取EleTree集合
     *
     * @return 树集合
     */
    List<EleTree<Department>> listEleTree();

    /**
     * 获取SelectTree集合
     *
     * @return 树集合
     */
    List<SelectTree<Department>> listSelectTree();
}
