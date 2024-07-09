package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 修改短链接分组请求实体
 * 类描述： ShortLinkGroupUpdateReqDTO
 **/
@Data
public class ShortLinkGroupUpdateReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;
}