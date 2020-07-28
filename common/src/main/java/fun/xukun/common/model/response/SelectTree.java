package fun.xukun.common.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 日期:2020/6/23
 * Select下拉框类
 *
 * @author xukun
 * @version 1.00
 */
@Data
public class SelectTree<T> implements Serializable {

    private String id;

    private String name;

    private boolean open;

    private List<SelectTree<T>> children;

    private String parentId;
}
