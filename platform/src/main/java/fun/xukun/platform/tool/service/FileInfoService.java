package fun.xukun.platform.tool.service;

import fun.xukun.model.domain.tool.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 日期:2020/8/3
 * 文件服务接口
 *
 * @author xukun
 * @version 1.00
 */
public interface FileInfoService {
    /**
     * 保存文件
     *
     * @param file      上传文件
     * @param creatorId 创建人主键
     * @return 文件信息
     */
    FileInfo insert(MultipartFile file, String creatorId);

    /**
     * 删除文件
     *
     * @param id 主键
     */
    void delete(String id);

    /**
     * 下载文件
     *
     * @param id       注解
     * @param response 响应
     */
    void download(String id, HttpServletResponse response);
}
