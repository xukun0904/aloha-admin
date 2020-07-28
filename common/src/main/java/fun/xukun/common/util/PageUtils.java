package fun.xukun.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.xukun.common.model.constant.Constants;
import fun.xukun.common.model.request.PageRequest;
import fun.xukun.common.model.response.PageResponse;

/**
 * 日期:2020/6/9
 * 分页工具类
 *
 * @author xukun
 * @version 1.00
 */
public class PageUtils {
    /**
     * 获取mybatis-plus分页对象
     *
     * @param request 分页参数
     */
    public static <T> IPage<T> getPage(PageRequest request) {
        Page<T> page = new Page<>();
        page.setCurrent(request.getPageNum());
        page.setSize(request.getPageSize());
        if (StringUtils.isNotBlank(request.getField())) {
            String field = StringUtils.camelToUnderscore(request.getField());
            // 默认正序排序
            String order = StringUtils.defaultIfBlank(request.getOrder(), Constants.ORDER_ASC);
            if (Constants.ORDER_DESC.equals(order)) {
                page.addOrder(OrderItem.desc(field));
            } else {
                page.addOrder(OrderItem.asc(field));
            }
        }
        return page;
    }

    /**
     * IPage对象转化成PageResponse对象
     *
     * @param page IPage对象
     * @param <T>  类型
     * @return PageResponse对象
     */
    public static <T> PageResponse<T> convertPageResponse(IPage<T> page) {
        PageResponse<T> response = new PageResponse<>();
        response.setTotal(page.getTotal());
        response.setRows(page.getRecords());
        return response;
    }
}
