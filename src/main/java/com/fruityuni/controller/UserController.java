package com.fruityuni.controller;

import com.fruityuni.dto.UserLoginDTO;
import com.fruityuni.dto.UserRegisterDTO;
import com.fruityuni.entity.User;
import com.fruityuni.service.UserService;
import com.fruityuni.vo.Result;
import com.fruityuni.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author fruityuni
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Void> register(@RequestBody @Validated UserRegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.success();
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginDTO loginDTO) {
        UserLoginVO loginVO = userService.login(loginDTO);
        return Result.success(loginVO);
    }

    @GetMapping("/info")
    @ApiOperation("获取当前登录用户信息")
    public Result<UserLoginVO> getUserInfo() {
        UserLoginVO userInfo = userService.getUserInfo();
        return Result.success(userInfo);
    }

    @PutMapping("/info")
    @ApiOperation("更新用户信息")
    public Result<Void> updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result<Object> getUserList() {
        // 实现用户列表查询，此处省略
        return Result.success();
    }
}