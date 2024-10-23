package com.gaoyifeng.IDaaS.domain.auth.service.code;

import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class CodeService implements ICodeService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    @Override
    public void getCode(String account, String type) {
        //验证是否已经绑定过？

        //生成验证码
        String code = RandomStringUtils.randomNumeric(4);
        //根据类型发送验证码

        //放入缓存
        userAccountRepository.putCacheCode(account,type,code);

    }

    @Override
    public void validCode(String flakeSnowId,String account, String code, String type) {
        //根据类型和账号从缓存中获取验证码

        //校验验证码

        //校验成功，执行绑定操作

    }
}
