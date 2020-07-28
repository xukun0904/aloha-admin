package fun.xukun.platform.system.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.xukun.model.domain.system.UserRole;
import fun.xukun.model.mapper.system.UserRoleMapper;
import fun.xukun.platform.system.manager.UserRoleManager;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色关联表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-06-23
 */
@Service
public class UserRoleManagerImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleManager {

}
