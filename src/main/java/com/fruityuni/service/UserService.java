package com.fruityuni.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fruityuni.dto.UserLoginDTO;
import com.fruityuni.dto.UserRegisterDTO;
import com.fruityuni.entity.User;
import com.fruityuni.vo.UserLoginVO;

/**
 * 用户服务接口
 *
 * @author fruityuni
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     */
    void register(UserRegisterDTO registerDTO);

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    UserLoginVO login(UserLoginDTO loginDTO);

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    UserLoginVO getUserInfo();

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    void updateUserInfo(User user);
}