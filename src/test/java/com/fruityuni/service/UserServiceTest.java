package com.fruityuni.service;

import com.fruityuni.dto.UserLoginDTO;
import com.fruityuni.dto.UserRegisterDTO;
import com.fruityuni.entity.User;
import com.fruityuni.exception.BusinessException;
import com.fruityuni.mapper.UserMapper;
import com.fruityuni.service.impl.UserServiceImpl;
import com.fruityuni.vo.UserLoginVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegister_Success() {
        // 准备测试数据
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");
        registerDTO.setPhone("13800138000");

        // Mock方法调用
        when(userMapper.selectCount(any())).thenReturn(0L);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // 执行测试
        assertDoesNotThrow(() -> userService.register(registerDTO));

        // 验证方法调用
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    public void testRegister_PasswordMismatch() {
        // 准备测试数据
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password456");

        // 执行测试并验证异常
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> userService.register(registerDTO));
        assertEquals("两次密码不一致", exception.getMessage());
    }

    @Test
    public void testRegister_UsernameExists() {
        // 准备测试数据
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        // Mock方法调用
        when(userMapper.selectCount(any())).thenReturn(1L);

        // 执行测试并验证异常
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> userService.register(registerDTO));
        assertEquals("用户名已存在", exception.getMessage());
    }
}