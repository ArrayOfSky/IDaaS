package com.gaoyifeng.IDaaS.infrastructure.repository;


import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserFreshTokenEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserFreshTokenRepository;
import com.gaoyifeng.IDaaS.infrastructure.dao.IUserFreshTokenDao;
import com.gaoyifeng.IDaaS.infrastructure.po.UserFreshTokenPo;
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
    public void save(String token, String userFlakeSnowId) {
        userFreshTokenDao.delete(userFlakeSnowId);

        UserFreshTokenPo userFreshTokenPo = new UserFreshTokenPo();
        userFreshTokenPo.setFreshToken(token);
        userFreshTokenPo.setUserFlakeSnowId(userFlakeSnowId);
        userFreshTokenPo.setCreateTime(new Date());

        userFreshTokenDao.save(userFreshTokenPo);
    }

    @Override
    public UserFreshTokenEntity get(String userFlakeSnowId) {
        UserFreshTokenPo userFreshTokenPo = userFreshTokenDao.get(userFlakeSnowId);
        if (userFreshTokenPo != null) {
            return new UserFreshTokenEntity(userFlakeSnowId, userFreshTokenPo.getFreshToken(),userFreshTokenPo.getCreateTime());
        }
        return null;
    }
}
