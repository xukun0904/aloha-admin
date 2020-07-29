package fun.xukun.platform.system.service;

import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.PageResponse;
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;
import fun.xukun.platform.security.model.UserInfo;

/**
 * 日期:2020/6/22
 * 用户服务接口
 *
 * @author xukun
 * @version 1.00
 */
public interface UserService {
    /**
     * 分页查询
     *
     * @param query   查询参数
     * @param request 分页参数
     * @return 分页结果
     */
    PageResponse<UserExt> list(UserQuery query, PageRequest request);

    /**
     * 更新
     *
     * @param bean 实体类
     */
    void update(UserExt bean);

    /**
     * 新增
     *
     * @param bean 实体类
     */
    void insert(UserExt bean);

    /**
     * 批量删除
     *
     * @param ids 主键字符
     */
    void multipleDelete(String ids);

    /**
     * 根据主键获取
     *
     * @param id 主键
     * @return 实体类
     */
    UserExt getById(String id);

    /**
     * 更新部分信息
     *
     * @param bean 实体类
     */
    void updatePatch(User bean);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    UserExt getByUsername(String username);

    /**
     * 修改密码
     *
     * @param currentUser 当前用户
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void updatePassword(UserInfo currentUser, String oldPassword, String newPassword);

    /**
     * 校验用户名是否没被占用
     *
     * @param username 用户名
     * @param id       用户主键
     * @return 是否没被占用
     */
    boolean verify(String username, String id);
}
