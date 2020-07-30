package fun.xukun.common.model.constant;

/**
 * 日期:2020/6/5
 * 常量类
 *
 * @author xukun
 * @version 1.00
 */
public interface Constants {
    /**
     * 前端页面路径前缀
     */
    String VIEW_PREFIX = "views/";

    /**
     * 顶级节点ID
     */
    String TOP_NODE_ID = "0";

    /**
     * 菜单顶级节点名称
     */
    String MENU_TOP_NODE_NAME = "菜单资源";

    /**
     * 部门顶级节点名称
     */
    String DEPARTMENT_TOP_NODE_NAME = "部门";

    /**
     * 部门有效
     */
    int DEPARTMENT_STATUS_ENABLE = 1;

    /**
     * 用户锁定
     */
    int USER_STATUS_LOCKED = 0;

    /**
     * 用户有效
     */
    int USER_STATUS_ENABLE = 1;

    /**
     * 倒序
     */
    String ORDER_DESC = "desc";

    /**
     * 顺序
     */
    String ORDER_ASC = "asc";

    /**
     * 菜单类型-目录
     */
    int MENU_TYPE_DIRECTORY = 1;

    /**
     * 初始密码
     */
    String DEFAULT_PASSWORD = "12345678";

    /**
     * 验证码key前缀
     */
    String LOGIN_CAPTCHA_PREFIX = "aloha:captcha:";
}
