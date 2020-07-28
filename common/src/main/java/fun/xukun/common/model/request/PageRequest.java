package fun.xukun.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日期:2020/3/26
 * 通用分页查询对象
 *
 * @author xukun
 * @version 1.00
 */
@Data
@NoArgsConstructor
@ApiModel(value = "PageRequest对象", description = "分页请求对象")
public class PageRequest {

    /**
     * 每页条数
     */
    @ApiModelProperty(value = "每页条数")
    private int pageSize;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private int pageNum;

    /**
     * 排序字段名
     */
    @ApiModelProperty(value = "排序字段名")
    private String field;

    /**
     * 排序规则，asc升序，desc降序
     */
    @ApiModelProperty(value = "排序规则，asc升序，desc降序")
    private String order;
}
