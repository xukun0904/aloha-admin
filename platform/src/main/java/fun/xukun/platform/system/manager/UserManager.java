package fun.xukun.platform.system.manager;

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
 * @since 2020-06-22
 */
public interface UserManager extends IService<User> {

   IPage<UserExt> listUsers(IPage<User> page, UserQuery query);

    UserExt getUserById(String id);

    UserExt getByUsername(String username);
}
