package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * URL标题控制层
 * 类描述： UrlTitleController
 **/
@RestController
@RequiredArgsConstructor
public class UrlTitleController {
    //    后续重构为spring feign调用
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService(){};
    /**
     * 根据url获取对应网站标题
     * @param url
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url){
        String title = shortLinkRemoteService.getTitleByUrl(url);
        return Results.success(title);
    }
}