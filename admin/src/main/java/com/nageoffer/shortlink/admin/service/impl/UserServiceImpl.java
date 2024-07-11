package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
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
                try{
                    int inserted=baseMapper.insert(BeanUtil.toBean(requestParam,UserDO.class));
                    if(inserted<1) {
                        throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                    }
                }catch(DuplicateKeyException exp){
                    throw new ClientException(UserErrorCodeEnum.USER_EXIST);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 根据用户名修改用户
     *
     * @param requestParam 修改用户请求参数
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
        //TODO 验证当前用户名是否为登录用户
        //由于分片表是根据用户名划分的
        LambdaQueryWrapper<UserDO> updateWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam,UserDO.class),updateWrapper);
    }

    /**
     * 用户登录
     * @param requestParam
     * @return
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if(userDO==null){
            throw new ClientException("用户不存在");
        }
        Boolean hasLogin = stringRedisTemplate.hasKey("login_" + requestParam.getUsername());
        if(hasLogin!=null&&hasLogin){
            throw new ClientException("用户已登录");
        }
        /**
         * Hash
         * key:login_用户名
         * value:
         *  value:token标识
         *  val:JSON字符串(用户信息)
         */
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put("login_"+requestParam.getUsername(),uuid,JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login_"+requestParam.getUsername(),30L, TimeUnit.DAYS);
        return new UserLoginRespDTO(uuid);
    }

    /**
     * 检查用户是否登录
     * @param username
     * @param token
     * @return
     */
    @Override
    public Boolean checkLogin(String username,String token) {
        return stringRedisTemplate.opsForHash().get("login_"+username,token)!=null;
    }

    /**
     * 退出登录
     * @param username
     * @param token
     */
    @Override
    public void logout(String username, String token) {
        if(checkLogin(username,token)){
            stringRedisTemplate.delete("login_"+username);
            return;
        }
        throw new ClientException("用户不存在或用户未登录");
    }
}