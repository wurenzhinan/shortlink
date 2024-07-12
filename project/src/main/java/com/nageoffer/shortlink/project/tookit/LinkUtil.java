package com.nageoffer.shortlink.project.tookit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static com.nageoffer.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * 短链接工具类
 * 类描述： LinkUtil
 **/
public class LinkUtil {
    /**
     * 获取短链接缓存有效期
     * @param validDate
     * @return
     */
    public static long getLinkCacheValidTime(Date validDate){
        return Optional.ofNullable(validDate)
                .map(each-> DateUtil.between(new Date(), DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }
}