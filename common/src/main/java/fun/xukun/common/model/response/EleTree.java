package fun.xukun.common.model.response;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 日期:2020/6/7
 * EleTree类型
 *
 * @author xukun
 * @version 1.00
 */
@Data
public class EleTree<T> implements Serializable {

    /**
     * 节点唯一标识
     */
    private String id;

    /**
     * 父节点标识
     */
    private String parentId;

    /**
     * 显示名称
     */
    private String label;

    /**
     * 是否选中
     */
    private boolean checked;

    /**
     * 是否可选中
     */
    private boolean disabled;

    /**
     * 是否是叶子节点
     */
    private boolean isLeaf;

    /**
     * 子节点
     */
    private List<EleTree<T>> children;

    /**
     * bean
     */
    private T data;

}