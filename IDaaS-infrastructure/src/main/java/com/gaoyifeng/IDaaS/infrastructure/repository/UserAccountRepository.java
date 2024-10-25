package com.gaoyifeng.IDaaS.infrastructure.repository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.CodeSendEntity;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.model.valobj.CodeTypeVo;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.infrastructure.dao.IUserAccountDao;
import com.gaoyifeng.IDaaS.infrastructure.po.UserAccountPo;
import com.gaoyifeng.IDaaS.infrastructure.redis.IRedisService;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.enums.RabbitMqModel;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserAccountRepository implements IUserAccountRepository {

    @Resource
    IUserAccountDao userAccountDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IRedisService redissonService;

    private final String CACHE_BOUND_EMAIL = "bound:email:";
    private final String CACHE_BOUND_PHONE = "bound:phone:";

    private Map<CodeTypeVo,String> cacheMap = new HashMap<>(2);

    public UserAccountRepository(){
        cacheMap.put(CodeTypeVo.BOUND_EMAIL,CACHE_BOUND_EMAIL);
        cacheMap.put(CodeTypeVo.BOUND_PHONE,CACHE_BOUND_PHONE);
    }


    @Override
    public void getCode(String account, String type,String code) {
        CodeSendEntity codeSendEntity = new CodeSendEntity();
        codeSendEntity.setAccount(account);
        codeSendEntity.setCode(code);
        codeSendEntity.setType(type);
        rabbitTemplate.convertAndSend(RabbitMqModel.EMAIL_QUEUE,RabbitMqModel.EMAIL_KEY+"message", JSON.toJSONString(codeSendEntity));
    }

    @Override
    public void putCacheCode(String account, String type, String code) {
        CodeTypeVo codeType = CodeTypeVo.getCodeType(type);
        String key = cacheMap.get(codeType);
        if(StringUtils.isEmpty(key)){
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER,"Invalid type: " + type);
        }
        redissonService.setValue(key + account,code,5000000);
    }

    @Override
    public UserAccountEntity selectUserByEmail(String account) {
        UserAccountPo userAccountPo = userAccountDao.selectUserByEmail(account);
        return  getUserAccountEntityByPo(userAccountPo);
    }

    @Override
    public UserAccountEntity selectUserByPhone(String account) {
        UserAccountPo userAccountPo = userAccountDao.selectUserByPhone(account);
        return  getUserAccountEntityByPo(userAccountPo);
    }

    @Override
    public String getCacheCode(String account, String type) {
        CodeTypeVo codeType = CodeTypeVo.getCodeType(type);
        String key = cacheMap.get(codeType);
        if(StringUtils.isEmpty(key)){
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER,"Invalid type: " + type);
        }
        return redissonService.getValue(key + account);
    }

    private UserAccountEntity getUserAccountEntityByPo(UserAccountPo userAccountPo){
        UserAccountEntity userAccount = null;
        if(userAccountPo!=null){
            userAccount = new UserAccountEntity();
            userAccount.setFlakeSnowId(userAccountPo.getFlakeSnowId());
            userAccount.setWxOpenId(userAccountPo.getWxOpenId());
            userAccount.setWxUserName(userAccountPo.getWxUserName());
            userAccount.setPhone(userAccountPo.getPhone());
            userAccount.setEmail(userAccountPo.getEmail());
            userAccount.setNickName(userAccountPo.getNickName());
            userAccount.setCreateTime(userAccountPo.getCreateTime());
            userAccount.setUpdateTime(userAccountPo.getUpdateTime());
            userAccount.setIcon(userAccountPo.getIcon());
        }
        return userAccount;
    }


}
