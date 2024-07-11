package com.nageoffer.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * 短链接分组查询返回参数
 * 类描述： ShortLinkGroupCountQueryRespDTO
 **/
@Data
public class ShortLinkGroupCountQueryRespDTO {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 分组下短链接的数量
     */
    private Integer shortLinkCount;
}