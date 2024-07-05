package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 返回实体
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper= Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername,username);
        UserDO userDO=baseMapper.selectOne(queryWrapper);
        UserRespDTO result=new UserRespDTO();
        if(userDO!=null){
            BeanUtils.copyProperties(userDO,result);
            return result;
        }else{
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return 存在，true 不存在，false
     */
    @Override
    public Boolean hashUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    /**
     * 注册用户
     *
     * @param requestParam
     */
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if(hashUsername(requestParam.getUsername())){
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY+requestParam.getUsername());
        try{
            if(lock.tryLock()){
                int inserted=baseMapper.insert(BeanUtil.toBean(requestParam,UserDO.class));
                if(inserted<1){
                    throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }finally {
            lock.unlock();
        }
    }
}