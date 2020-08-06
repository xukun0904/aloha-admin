package fun.xukun.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.io.File;
import java.io.IOException;

/**
 * 日期:2020/8/3
 * 文件相关工具类
 *
 * @author xukun
 * @version 1.00
 */
@Slf4j
public class FileUtils {

    /**
     * 获取唯一文件名
     *
     * @return 文件名
     */
    public static String generateFileName() {
        return IdWorker.getIdStr();
    }


    /**
     * 获取不带后缀的文件名
     *
     * @param fullName 全文件名
     * @return 不带后缀的文件名
     */
    public static String getName(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            int dotIndex = fullName.lastIndexOf('.');
            return (dotIndex == -1) ? fullName : fullName.substring(0, dotIndex);
        }
        return "";
    }

    /**
     * 获取文件后缀名
     *
     * @param fullName 文件名
     * @return 后缀名
     */
    public static String getFileExtension(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            int dotIndex = fullName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fullName.substring(dotIndex);
        }
        return "";
    }

    /**
     * 根据资源类型获取文件后缀名
     *
     * @param contentType 资源类型
     * @return 后缀名
     */
    public static String getExtension(String contentType) {
        try {
            MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
            MimeType jpeg = allTypes.forName(contentType);
            return jpeg.getExtension();
        } catch (MimeTypeException e) {
            e.printStackTrace();
            log.error("获取后缀名失败", e);
        }
        return "";
    }

    /**
     * 根据后缀名获取文件类型
     *
     * @param suffix 后缀名
     * @return 文件类型
     */
    public static String getFileType(String suffix) {
        String documents = "txt doc pdf ppt pps xlsx xls docx";
        String music = "mp3 wav wma mpa ram ra aac aif m4a";
        String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg";
        if (image.contains(suffix)) {
            return "图片";
        } else if (documents.contains(suffix)) {
            return "文档";
        } else if (music.contains(suffix)) {
            return "音乐";
        } else if (video.contains(suffix)) {
            return "视频";
        } else {
            return "其他";
        }
    }

    /**
     * 创建父目录
     *
     * @param file 文件
     * @return 是否成功
     */
    public static boolean createParentDirs(File file) {
        try {
            File parent = file.getCanonicalFile().getParentFile();
            if (parent == null) {
                return false;
            }
            return parent.mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("创建父目录异常", e);
        }
        return false;
    }

    /**
     * 静默删除文件
     *
     * @param file 文件
     * @return 是否成功
     */
    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            return file.delete();
        } catch (final Exception ignored) {
            return false;
        }
    }
}
