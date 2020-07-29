package fun.xukun.model.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xukun
 * @since 2020-06-22
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 分页结果
     */
    IPage<UserExt> listUsers(IPage<User> page, @Param("query") UserQuery query);

    /**
     * 根据主键获取用户
     *
     * @param id 主键
     * @return 用户
     */
    UserExt getUserById(@Param("id") String id);

    /**
     * 根据用户名获取用户
     *
     * @param username 主键
     * @return 用户
     */
    UserExt getByUsername(@Param("username") String username);
}
