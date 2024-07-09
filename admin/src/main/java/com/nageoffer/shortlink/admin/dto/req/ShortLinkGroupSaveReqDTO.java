/*
 *   Copyright © 2018 重庆市信息通信咨询设计院有限公司版权所有.
 *
 *   项目名称：shortlink
 *   文件名称：com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO
 *
 *   创建人：  LI WEI
 *   创建日期：2024/7/9
 *
 *   版权描述：此软件未经重庆市信息通信咨询设计院有限公司许可，严禁发布、传播、使用.
 *   公司地址：重庆市九龙坡区科园四路257号,400041.
 *
 */


package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 类描述： ShortLinkGroupSaveReqDTO
 **/
@Data
public class ShortLinkGroupSaveReqDTO {
    /**
     * 分组名称
     */
    private String name;
}