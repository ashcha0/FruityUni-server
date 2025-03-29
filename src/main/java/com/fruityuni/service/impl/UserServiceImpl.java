package com.fruityuni.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fruityuni.dto.UserLoginDTO;
import com.fruityuni.dto.UserRegisterDTO;
import com.fruityuni.entity.User;
import com.fruityuni.exception.BusinessException;
// 修改导入，使用自定义的AuthenticationException
import com.fruityuni.exception.AuthenticationException;
import com.fruityuni.mapper.UserMapper;
import com.fruityuni.service.UserService;
import com.fruityuni.util.JwtTokenUtil;
import com.fruityuni.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 用户服务实现类
 *
 * @author fruityuni
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void register(UserRegisterDTO registerDTO) {
        // 校验两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new BusinessException("两次密码不一致");
        }

        // 校验用户名是否已存在
        LambdaQueryWrapper<User> usernameWrapper = new LambdaQueryWrapper<>();
        usernameWrapper.eq(User::getUsername, registerDTO.getUsername());
        if (userMapper.selectCount(usernameWrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 校验手机号是否已存在
        LambdaQueryWrapper<User> phoneWrapper = new LambdaQueryWrapper<>();
        phoneWrapper.eq(User::getPhone, registerDTO.getPhone());
        if (userMapper.selectCount(phoneWrapper) > 0) {
            throw new BusinessException("手机号已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPhone(registerDTO.getPhone());
        user.setNickname(registerDTO.getNickname());
        user.setStatus(1);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        userMapper.insert(user);
    }

    @Override
    public UserLoginVO login(UserLoginDTO loginDTO) {
        try {
            // 认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成token - 使用用户名而不是UserDetails对象
            String token = jwtTokenUtil.generateToken(loginDTO.getUsername());

            // 构建登录返回信息
            UserLoginVO userLoginVO = new UserLoginVO();
            // 设置token和其他用户信息
            userLoginVO.setToken(token);
            userLoginVO.setUsername(loginDTO.getUsername());
            
            return userLoginVO;
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new com.fruityuni.exception.AuthenticationException("用户名或密码错误");
        }
    }
    
    @Override
    public void updateUserInfo(User user) {
        try {
            // 实现更新用户信息的方法
            user.setUpdatedTime(LocalDateTime.now());
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new AuthenticationException("更新用户信息失败");
        }
    }
    
    @Override
    public UserLoginVO getUserInfo() {
        // 获取当前登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("用户未登录");
        }
        
        String username = authentication.getName();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        
        if (user == null) {
            throw new AuthenticationException("用户不存在");
        }
        
        // 构建返回信息
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUsername(username);
        
        return userLoginVO;
    }
}