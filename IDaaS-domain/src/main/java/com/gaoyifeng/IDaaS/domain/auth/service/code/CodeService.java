package com.gaoyifeng.IDaaS.domain.auth.service.code;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.CodeSendEntity;
import com.gaoyifeng.IDaaS.domain.auth.model.valobj.CodeTypeVo;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.bound.EmailBoundService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.bound.PhoneBoundService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.filter.EmailFilterService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.filter.PhoneFilterService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.send.EmailSendService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.send.MessageSendService;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CodeService implements ICodeService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    private Map<CodeTypeVo, ISendMessageService> codeSendMap = new HashMap<>(2);

    private Map<CodeTypeVo, ISendFilterService> codeFilterMap = new HashMap<>(2);

    private Map<CodeTypeVo, IVerifyMessageService> codeVerifyMap = new HashMap<>(2);

    public CodeService(EmailSendService emailSendService, MessageSendService messageSendService,
                       EmailFilterService emailFilterService, PhoneFilterService phoneFilterService,
                       EmailBoundService emailBoundService,
                       PhoneBoundService phoneBoundService) {

        codeSendMap.put(CodeTypeVo.BOUND_EMAIL, emailSendService);
        codeSendMap.put(CodeTypeVo.BOUND_PHONE, messageSendService);

        codeFilterMap.put(CodeTypeVo.BOUND_EMAIL, emailFilterService);
        codeFilterMap.put(CodeTypeVo.BOUND_PHONE, phoneFilterService);

        codeVerifyMap.put(CodeTypeVo.BOUND_EMAIL, emailBoundService);
        codeVerifyMap.put(CodeTypeVo.BOUND_PHONE, phoneBoundService);
    }

    @Override
    public void getCode(String account, String type) {
        log.info("生成验证码");
        String code = RandomStringUtils.randomNumeric(4);
        log.info("发送异步信息" + code);
        userAccountRepository.getCode(account, type, code);
    }

    @Override
    public void sendCode(CodeSendEntity codeSendEntity) {
        CodeTypeVo codeType = CodeTypeVo.getCodeType(codeSendEntity.getType());
        log.info("根据codeType判断是否需要进行过滤条件");
        ISendFilterService sendFilterService = codeFilterMap.get(codeType);
        if (sendFilterService != null) {
            sendFilterService.doFilter(codeSendEntity.getAccount());
        }
        log.info("根据类型发送验证码");
        ISendMessageService iSendMessageService = codeSendMap.get(codeType);
        if (iSendMessageService == null) {
            throw new BaseException(Constants.ResponseCode.UN_ERROR, "未知错误，请稍后再试");
        }
        iSendMessageService.sendMessage(codeSendEntity.getAccount(), codeSendEntity.getCode());
        log.info("验证码发送成功，保存到缓存中");
        userAccountRepository.putCacheCode(codeSendEntity.getAccount(), codeSendEntity.getType(), codeSendEntity.getCode());
    }

    @Override
    public void validCode(String flakeSnowId, String account, String code, String type) {
        //根据类型和账号从缓存中获取验证码
        log.info("根据类型和账号从缓存中获取验证码");
        String cacheCode = userAccountRepository.getCacheCode(account, type);
        //校验验证码
        log.info("校验验证码");
        if (!code.equals(cacheCode)) {
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "验证码错误");
        }
        //校验成功，执行绑定操作
        log.info("校验成功，执行绑定操作");
        codeVerifyMap.get(CodeTypeVo.getCodeType(type)).verifyMessage(flakeSnowId, account);
    }
}
