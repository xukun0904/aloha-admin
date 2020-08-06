package fun.xukun.platform.tool.service.impl;

import fun.xukun.common.exception.ExceptionCast;
import fun.xukun.common.model.response.CommonCode;
import fun.xukun.common.util.FileUtils;
import fun.xukun.common.util.StringUtils;
import fun.xukun.model.domain.tool.FileInfo;
import fun.xukun.model.manager.tool.FileInfoManager;
import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.tool.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * 日期:2020/8/3
 * 文件服务实现类
 *
 * @author xukun
 * @version 1.00
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoManager fileInfoManager;

    private final AlohaProperties alohaProperties;

    @Override
    public FileInfo insert(MultipartFile file, String creatorId) {
        try {
            String fullName = file.getOriginalFilename();
            // 获取没有后缀的文件名
            String realName = FileUtils.getName(fullName);
            FileInfo fileInfo = new FileInfo();
            // 文件名
            fileInfo.setRealName(realName);
            // 生成唯一文件名
            String filename = FileUtils.generateFileName();
            fileInfo.setName(filename);
            // 获取文件名后缀
            String suffix = FileUtils.getFileExtension(fullName);
            if (StringUtils.isBlank(suffix)) {
                suffix = FileUtils.getExtension(file.getContentType());
            }
            fileInfo.setSuffix(suffix);
            // 文件类型
            String fileType = FileUtils.getFileType(suffix);
            fileInfo.setType(fileType);
            // 真实文件大小
            fileInfo.setSize(file.getSize());
            // 存储位置
            String path = alohaProperties.getFilePath() + File.separator + filename + suffix;
            fileInfo.setPath(path);
            // 标签
            fileInfo.setLabel("头像");
            // 本地保存文件
            File dest = new File(path);
            FileUtils.createParentDirs(dest);
            file.transferTo(dest);
            fileInfo.setCreatorId(creatorId);
            fileInfo.setCreateTime(LocalDateTime.now());
            boolean isSuccess = this.fileInfoManager.save(fileInfo);
            if (!isSuccess) {
                ExceptionCast.cast(CommonCode.SAVE_FAIL);
            }
            return fileInfo;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件保存失败", e);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        FileInfo fileInfo = fileInfoManager.getById(id);
        String path = fileInfo.getPath();
        File file = new File(path);
        // 删除本地文件
        FileUtils.deleteQuietly(file);
        // 删除对象
        fileInfoManager.removeById(id);
    }

    @Override
    public void download(String id, HttpServletResponse response) {
        try {
            FileInfo fileInfo = this.fileInfoManager.getById(id);
            String realName = fileInfo.getRealName();
            realName += fileInfo.getSuffix();
            // 写之前设置响应流以附件的形式打开返回值,这样可以保证前边打开文件出错时异常可以返回给前台
            response.setHeader("Content-Disposition", "attachment;filename=" + realName);
            String path = fileInfo.getPath();
            ServletOutputStream outputStream = response.getOutputStream();
            Files.copy(Paths.get(path), outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件下载失败", e);
        }
    }
}
