package fun.xukun.model.manager.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.xukun.model.domain.system.RoleMenu;
import fun.xukun.model.manager.system.RoleMenuManager;
import fun.xukun.model.mapper.system.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-06-09
 */
@Service
public class RoleMenuManagerImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuManager {

}
