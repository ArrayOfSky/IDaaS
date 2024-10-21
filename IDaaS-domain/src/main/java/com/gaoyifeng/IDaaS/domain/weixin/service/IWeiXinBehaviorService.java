package com.gaoyifeng.IDaaS.domain.weixin.service;


import com.gaoyifeng.IDaaS.domain.weixin.model.entity.UserBehaviorMessageEntity;

/**
 * @description 受理用户行为接口
 */
public interface IWeiXinBehaviorService {

    String acceptUserBehavior(UserBehaviorMessageEntity userBehaviorMessageEntity);

}
