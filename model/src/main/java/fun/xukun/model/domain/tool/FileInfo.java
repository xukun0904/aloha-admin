package fun.xukun.model.domain.tool;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 本地文件表
 * </p>
 *
 * @author xukun
 * @since 2020-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("file_info")
@ApiModel(value="FileInfo对象", description="本地文件表")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文件真实的名称")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "本地存储文件名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "后缀")
    @TableField("suffix")
    private String suffix;

    @ApiModelProperty(value = "路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "大小")
    @TableField("size")
    private Long size;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "标签")
    @TableField("label")
    private String label;

    @ApiModelProperty(value = "创建人主键")
    @TableField("creator_id")
    private String creatorId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
