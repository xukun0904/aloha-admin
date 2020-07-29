package fun.xukun.model.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.xukun.model.domain.system.Role;
import fun.xukun.model.mapper.system.RoleMapper;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xukun
 * @since 2020-06-09
 */
public interface RoleManager extends IService<Role> {
    /**
     * 获取Mapper接口
     *
     * @return Mapper接口
     */
    @Override
    RoleMapper getBaseMapper();
}
