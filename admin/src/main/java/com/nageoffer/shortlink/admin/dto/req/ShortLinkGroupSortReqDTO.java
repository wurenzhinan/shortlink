package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 类描述： ShortLinkGroupSaveReqDTO
 **/
@Data
public class ShortLinkGroupSortReqDTO {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 分组排序
     */
    private Integer sortOrder;

}