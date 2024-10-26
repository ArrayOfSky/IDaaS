package com.gaoyifeng.IDaaS.infrastructure.dao;

import com.gaoyifeng.IDaaS.infrastructure.po.UserAccountPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserAccountDao {
    UserAccountPo selectUserByEmail(@Param("account") String account);

    UserAccountPo selectUserByPhone(@Param("account") String account);

    void insertUserAccount(@Param("userAccount") UserAccountPo userAccount);

    void updateUserAccount(@Param("userAccount") UserAccountPo userAccount);

    UserAccountPo selectUserByFlakeSnowId(@Param("flakeSnowId") String flakeSnowId);
}
