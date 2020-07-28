package fun.xukun.common.model.response;

import lombok.Data;

import java.util.List;

/**
 * 日期:2020/6/9
 * 分页响应值
 *
 * @author xukun
 * @version 1.00
 */
@Data
public class PageResponse<T> {

    /**
     * 列表
     */
    private List<T> rows;

    /**
     * 总数量
     */
    private long total;

}
