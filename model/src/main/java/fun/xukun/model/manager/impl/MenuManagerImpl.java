package fun.xukun.model.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.xukun.model.domain.system.Menu;
import fun.xukun.model.manager.MenuManager;
import fun.xukun.model.mapper.system.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-06-09
 */
@Service
public class MenuManagerImpl extends ServiceImpl<MenuMapper, Menu> implements MenuManager {

    @Override
    public List<Menu> listMenuTreeByUserId(String userId) {
        return this.getBaseMapper().listMenuTreeByUserId(userId);
    }
}
