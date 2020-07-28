package fun.xukun.model.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.xukun.model.domain.system.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author xukun
 * @since 2020-06-19
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户主键查询可查看的菜单
     *
     * @param userId 用户主键
     * @return 菜单列表
     */
    List<Menu> listMenuTreeByUserId(String userId);
}
