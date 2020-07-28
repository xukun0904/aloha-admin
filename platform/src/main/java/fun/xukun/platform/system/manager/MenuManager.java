package fun.xukun.platform.system.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.xukun.model.domain.system.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author xukun
 * @since 2020-06-09
 */
public interface MenuManager extends IService<Menu> {

    /**
     * 根据用户主键查询可查看的菜单
     *
     * @param userId 用户主键
     * @return 菜单列表
     */
    List<Menu> listMenuTreeByUserId(String userId);
}
