package fun.xukun.platform.system.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.mapper.system.RoleMapper;
import fun.xukun.platform.system.manager.RoleManager;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-06-09
 */
@Service
public class RoleManagerImpl extends ServiceImpl<RoleMapper, Role> implements RoleManager {
}
