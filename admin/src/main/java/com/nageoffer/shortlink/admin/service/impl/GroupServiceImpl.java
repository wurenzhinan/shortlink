package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.biz.user.UserContext;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dao.mapper.GroupMapper;
import com.nageoffer.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.nageoffer.shortlink.admin.service.GroupService;
import com.nageoffer.shortlink.admin.tookit.RandomStringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接分组接口实现层
 * 类描述： GroupServiceImpl
 **/
@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    /**
     * 新增短链接分组
     *
     * @param groupName
     */
    @Override
    public void save(String groupName) {
        String gid;
        while(true){
            gid=RandomStringGenerator.generateRandomString();
            if(hasGid(gid)){
                break;
            }
        }
        GroupDO groupDO=GroupDO.builder()
                .gid(gid)
                .username(UserContext.getUsername())
                .name(groupName)
                .sortOrder(0)
                .build();
        baseMapper.insert(groupDO);
    }

    /**
     * 查询用户短链接分组集合
     *
     * @return
     */
    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOList,ShortLinkGroupRespDTO.class);
    }

    /**
     * 修改短链接分组
     *
     * @param requestParam
     */
    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaQueryWrapper<GroupDO> updateWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, requestParam.getGid());
        GroupDO groupDO=new GroupDO();
        groupDO.setName(requestParam.getName());
        baseMapper.update(groupDO,updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO groupDO=new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO,queryWrapper);
    }

    //检查gid是否已存在，保证gid唯一
    public boolean hasGid(String gid){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO hasGroupFlag=baseMapper.selectOne(queryWrapper);
        return hasGroupFlag==null;
    }
}