package com.fruityuni.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fruityuni.dto.UserLoginDTO;
import com.fruityuni.dto.UserRegisterDTO;
import com.fruityuni.entity.User;
import com.fruityuni.exception.BusinessException;
import com.fruityuni.mapper.UserMapper;
import com.fruityuni.service.UserService;
import com.fruityuni.util.JwtTokenUtil;
import com.fruityuni.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 用户服务实现类
 *
 * @author fruityuni
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDTO registerDTO) {
        // 验证两次密码是否一致
        if (!Objects.equals(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
            throw new BusinessException("两次密码不一致");
        }

        // 验证用户名是否已存在
        long count = count(new LambdaQueryWrapper<User>().eq(User::getUsername, registerDTO.getUsername()));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 验证手机号是否已存在
        count = count(new LambdaQueryWrapper<User>().eq(User::getPhone, registerDTO.getPhone()));
        if (count > 0) {
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

        // 保存用户
        save(user);

        // 分配普通用户角色
        userMapper.insertUserRole(user.getId(), 2L); // 2L为普通用户角色ID
    }

    @Override
    public UserLoginVO login(UserLoginDTO loginDTO) {
        // 认证用户
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        // 设置认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //