package com.nageoffer.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (TUser)实体类
 *
 * @author makejava
 * @since 2024-07-04 14:31:32
 */
@Data
@TableName("t_user")
public class UserDO {
/**
     * ID
     */
    private Long id;
/**
     * 用户名
     */
    private String username;
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

