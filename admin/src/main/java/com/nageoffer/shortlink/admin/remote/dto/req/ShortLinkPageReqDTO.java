package com.nageoffer.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.remote.dao.ShortLinkDO;
import lombok.Data;

/**
 * 短链接分页请求参数
 * 类描述： ShortLinkPageReqDTO
 **/
@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {
    /**
     * 分组标识
     */
    private String gid;
}