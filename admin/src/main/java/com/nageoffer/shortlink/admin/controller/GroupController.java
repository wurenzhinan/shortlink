package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.nageoffer.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组控制层
 * 类描述： GroupController
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-link/v1/")
public class GroupController {
    private final GroupService groupService;

    /**
     * 新增短链接分组
     * @param requstParam
     * @return
     */
    @PostMapping("group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO requstParam){
        groupService.save(requstParam.getName());
        return Results.success();
    }

    /**
     * 查询用户短链接分组集合
     *
     * @return
     */
    @GetMapping("/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup(){
        List<ShortLinkGroupRespDTO> result = groupService.listGroup();
        return Results.success(result);
    }
}