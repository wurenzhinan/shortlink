package com.nageoffer.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * 类描述： UserRespDTO
 **/

/**
 * 用户返回参数响应
 */
@Data
public class UserActualRespDTO {
    /**
     * ID
     */
    private Long id;
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