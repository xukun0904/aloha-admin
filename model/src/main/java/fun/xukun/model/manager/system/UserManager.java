package fun.xukun.model.manager.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xukun
 * @since 2020-08-03
 */
public interface UserManager extends IService<User> {
    /**
     * 分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 分页结果
     */
    IPage<UserExt> listUsers(IPage<User> page, UserQuery query);

    /**
     * 根据主键获取用户
     *
     * @param id 主键
     * @return 用户
     */
    UserExt getUserById(String id);

    /**
     * 根据用户名获取用户
     *
     * @param username 主键
     * @return 用户
     */
    UserExt getByUsername(String username);
}
