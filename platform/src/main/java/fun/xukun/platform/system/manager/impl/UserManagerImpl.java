package fun.xukun.platform.system.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.xukun.model.domain.system.User;
import fun.xukun.model.domain.system.ext.UserExt;
import fun.xukun.model.domain.system.request.UserQuery;
import fun.xukun.model.mapper.system.UserMapper;
import fun.xukun.platform.system.manager.UserManager;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-06-22
 */
@Service
public class UserManagerImpl extends ServiceImpl<UserMapper, User> implements UserManager {

    @Override
    public IPage<UserExt> listUsers(IPage<User> page,UserQuery query) {
        return this.baseMapper.listUsers(page, query);
    }

    @Override
    public UserExt getUserById(String id) {
        return this.baseMapper.getUserById(id);
    }

    @Override
    public UserExt getByUsername(String username) {
        return this.baseMapper.getByUsername(username);
    }
}
