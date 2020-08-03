package fun.xukun.model.manager.system.impl;

import fun.xukun.model.domain.system.FileInfo;
import fun.xukun.model.mapper.system.FileInfoMapper;
import fun.xukun.model.manager.system.FileInfoManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 本地文件表 服务实现类
 * </p>
 *
 * @author xukun
 * @since 2020-08-03
 */
@Service
public class FileInfoManagerImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoManager {

}
