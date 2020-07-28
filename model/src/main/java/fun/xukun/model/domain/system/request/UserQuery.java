package fun.xukun.model.domain.system.request;

import fun.xukun.common.model.request.RequestData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日期:2020/6/23
 *
 * @author xukun
 * @version 1.00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends RequestData {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 创建时间范围开始
     */
    private String createTimeStart;

    /**
     * 创建时间范围结束
     */
    private String createTimeEnd;

    /**
     * 状态
     */
    private Integer status;
}
