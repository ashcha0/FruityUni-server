package com.fruityuni.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fruityuni.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author fruityuni
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 插入用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    @Insert("INSERT INTO user_role(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 查询用户角色
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    @Select("SELECT r.code FROM role r JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = #{userId} AND r.status = 1 AND r.deleted = 0")
    List<String> selectUserRoles(@Param("userId") Long userId);

    /**
     * 查询用户权限
     *
     * @param userId 用户ID
     * @return 权限编码列表
     */
    @Select("SELECT DISTINCT p.code FROM permission p JOIN role_permission rp ON p.id = rp.permission_id JOIN user_role ur ON rp.role_id = ur.role_id WHERE ur.user_id = #{userId} AND p.status = 1 AND p.deleted = 0")
    List<String> selectUserPermissions(@Param("userId") Long userId);
}