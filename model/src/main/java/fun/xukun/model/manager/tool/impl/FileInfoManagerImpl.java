package fun.xukun.model.manager.tool.impl;

import fun.xukun.model.domain.tool.FileInfo;
import fun.xukun.model.mapper.tool.FileInfoMapper;
import fun.xukun.model.manager.tool.FileInfoManager;
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
