package com.nageoffer.shortlink.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短链接创建响应对象
 * 类描述： ShortLinkCreateRespDTO
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkCreateRespDTO {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 原始链接
     */
    private String originUrl;
    /**
     * 短链接
     */
    private String fullShortUrl;
}