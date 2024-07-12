package com.nageoffer.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * 回收站保存功能
 * 类描述： RecycleBinSaveReqDTO
 **/
@Data
public class RecycleBinSaveReqDTO {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 全部短链接
     */
    private String fullShortUrl;
}