package fun.xukun.platform.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fun.xukun.common.model.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 日期:2020/6/30
 * 登录用户对象
 *
 * @author xukun
 * @version 1.00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements UserDetails {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 是否开启tab页
     */
    private Integer isTab;

    /**
     * 主题颜色
     */
    private Integer theme;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == Constants.USER_STATUS_ENABLE;
    }
}
