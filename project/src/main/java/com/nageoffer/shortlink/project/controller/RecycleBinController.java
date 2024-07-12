package com.nageoffer.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.project.common.convention.result.Result;
import com.nageoffer.shortlink.project.common.convention.result.Results;
import com.nageoffer.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 回收站控制层
 * 类描述： RecycleBinController
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-link/v1/recycle-bin/")
public class RecycleBinController {
    private final RecycleBinService recycleBinService;

    /**
     *保存回收站
     * @param requestParam
     * @return
     */
    @PostMapping("save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam){
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }
    /**
     * 分页查询回收站短链接
     * @param requestParam
     * @return
     */
    @GetMapping("page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return Results.success(recycleBinService.pageShortLink(requestParam));
    }
}