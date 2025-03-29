package com.fruityuni.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List; // 添加List的导入

/**
 * 用户登录视图对象
 *
 * @author fruityuni
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户登录视图对象")
public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "用户ID")
    private Long id;
    
    @ApiModelProperty(value = "用户名")
    private String username;
    
    @ApiModelProperty(value = "昵称")
    private String nickname;
    
    @ApiModelProperty(value = "手机号")
    private String phone;
    
    @ApiModelProperty(value = "头像URL")
    private String avatar;
    
    @ApiModelProperty(value = "性别：0未知，1男，2女")
    private Integer gender;
    
    @ApiModelProperty(value = "JWT令牌")
    private String token;
    
    @ApiModelProperty(value = "角色列表")
    private String[] roles;
    
    @ApiModelProperty(value = "权限列表")
    private String[] permissions;

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions.toArray(new String[0]);
    }
}