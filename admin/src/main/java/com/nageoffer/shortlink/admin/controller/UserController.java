package com.nageoffer.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserActualRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-link/v1")
public class UserController {
    private  final UserService userService;
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        UserRespDTO result=userService.getUserByUsername(username);
        return Results.success(result);
    }
    /**
     * 根据用户名查询无脱敏用户信息
     * @param username
     * @return
     */
    @GetMapping("/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username){
        UserActualRespDTO result= BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class);
        return Results.success(result);
    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    @GetMapping("/user/hash-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hashUsername(username));
    }

    /**
     * 注册用户
     * @param requestParam
     * @return
     */
    @PostMapping("/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户信息
     * @param requestParam
     * @return
     */
    @PutMapping("/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam){
        userService.update(requestParam);
        return Results.success();
    }

}