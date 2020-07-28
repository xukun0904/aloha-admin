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

    IPage<UserExt> listUsers(IPage<User> page, @Param("query") UserQuery query);

    UserExt getUserById(@Param("id") String id);

    UserExt getByUsername(@Param("username") String username);
}
