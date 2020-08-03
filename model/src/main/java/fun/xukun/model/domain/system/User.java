package fun.xukun.model.domain.system;

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
 * 用户表
 * </p>
 *
 * @author xukun
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "部门主键")
    @TableField("department_id")
    private String departmentId;

    @ApiModelProperty(value = "性别 0男 1女")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "状态 0锁定 1有效")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "是否开启tab 0关闭 1开启")
    @TableField("is_tab")
    private Integer isTab;

    @ApiModelProperty(value = "主题 0黑 1白")
    @TableField("theme")
    private Integer theme;

    @ApiModelProperty(value = "头像")
    @TableField("avatar_id")
    private String avatarId;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
