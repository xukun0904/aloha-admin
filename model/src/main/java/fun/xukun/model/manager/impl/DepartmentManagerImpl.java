package fun.xukun.model.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.xukun.model.domain.system.Department;
import fun.xukun.model.manager.DepartmentManager;
import fun.xukun.model.mapper.system.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-06-19
 */
@Service
public class DepartmentManagerImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentManager {

}
