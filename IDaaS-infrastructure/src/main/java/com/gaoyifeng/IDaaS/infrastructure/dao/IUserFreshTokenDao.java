package com.gaoyifeng.IDaaS.infrastructure.dao;


import com.gaoyifeng.IDaaS.infrastructure.po.UserFreshTokenPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserFreshTokenDao {

    void delete(@Param("userFlakeSnowId") String userFlakeSnowId);
    void save(@Param("userFreshTokenPo") UserFreshTokenPo userFreshTokenPo);

    UserFreshTokenPo get(@Param("userFlakeSnowId") String userFlakeSnowId);
    UserFreshTokenPo getByToken( @Param("refreshToken") String refreshToken);

}
