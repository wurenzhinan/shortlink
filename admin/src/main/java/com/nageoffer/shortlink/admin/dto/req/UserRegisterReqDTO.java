/*
 *   Copyright © 2018 重庆市信息通信咨询设计院有限公司版权所有.
 *
 *   项目名称：shortlink
 *   文件名称：com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO
 *
 *   创建人：  LI WEI
 *   创建日期：2024/7/5
 *
 *   版权描述：此软件未经重庆市信息通信咨询设计院有限公司许可，严禁发布、传播、使用.
 *   公司地址：重庆市九龙坡区科园四路257号,400041.
 *
 */


package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 类描述： UserRegisterReqDTO
 **/
@Data
public class UserRegisterReqDTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String mail;
}