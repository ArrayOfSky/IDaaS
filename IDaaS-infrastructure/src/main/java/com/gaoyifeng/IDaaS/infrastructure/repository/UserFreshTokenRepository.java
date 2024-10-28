package com.gaoyifeng.IDaaS.infrastructure.repository;


import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserFreshTokenEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserFreshTokenRepository;
import com.gaoyifeng.IDaaS.infrastructure.dao.IUserFreshTokenDao;
import com.gaoyifeng.IDaaS.infrastructure.po.UserFreshTokenPo;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Slf4j
public class UserFreshTokenRepository implements IUserFreshTokenRepository {

    @Resource
    private IUserFreshTokenDao userFreshTokenDao;

    @Override
    public void save(String freshToken, String userFlakeSnowId, String token) {
        UserFreshTokenPo userFreshTokenPo = new UserFreshTokenPo();
        userFreshTokenPo.setFreshToken(freshToken);
        userFreshTokenPo.setUserFlakeSnowId(userFlakeSnowId);
        userFreshTokenPo.setCreateTime(new Date());
        userFreshTokenPo.setToken(token);
        userFreshTokenDao.save(userFreshTokenPo);
    }

    @Override
    public UserFreshTokenEntity get(String userFlakeSnowId) {
        UserFreshTokenPo userFreshTokenPo = userFreshTokenDao.get(userFlakeSnowId);
        if (userFreshTokenPo != null) {
            return new UserFreshTokenEntity(userFlakeSnowId, userFreshTokenPo.getFreshToken(), userFreshTokenPo.getCreateTime(),userFreshTokenPo.getToken());
        }
        return null;
    }

    @Override
    public void remove(String userFlakeSnowId) {
        userFreshTokenDao.delete(userFlakeSnowId);
    }

    @Override
    public UserFreshTokenEntity getByToken(String refreshToken) {
        UserFreshTokenPo userFreshTokenPo = userFreshTokenDao.getByToken(refreshToken);
        if(userFreshTokenPo == null) {
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER,"用户刷新令牌不存在!");
        }
        UserFreshTokenEntity userFreshTokenEntity = new UserFreshTokenEntity();
        userFreshTokenEntity.setUserFlakeSnowId(userFreshTokenPo.getUserFlakeSnowId());
        userFreshTokenEntity.setCreateTime(userFreshTokenPo.getCreateTime());
        userFreshTokenEntity.setFreshToken(userFreshTokenPo.getFreshToken());
        userFreshTokenEntity.setToken(userFreshTokenPo.getToken());
        userFreshTokenEntity.setFreshToken(refreshToken);
        return userFreshTokenEntity;
    }
}
