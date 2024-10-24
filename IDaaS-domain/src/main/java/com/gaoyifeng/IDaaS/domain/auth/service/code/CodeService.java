package com.gaoyifeng.IDaaS.domain.auth.service.code;

import com.gaoyifeng.IDaaS.domain.auth.model.valobj.CodeTypeVo;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.filter.EmailFilterService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.filter.PhoneFilterService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.message.EmailSendService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.message.MessageSendService;
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

    public CodeService(EmailSendService emailSendService, MessageSendService messageSendService, EmailFilterService emailFilterService, PhoneFilterService phoneFilterService){

        codeSendMap.put(CodeTypeVo.BOUND_EMAIL,emailSendService);
        codeSendMap.put(CodeTypeVo.BOUND_PHONE,messageSendService);

        codeFilterMap.put(CodeTypeVo.BOUND_EMAIL,emailFilterService);
        codeFilterMap.put(CodeTypeVo.BOUND_PHONE,phoneFilterService);
    }

    @Override
    public void getCode(String account, String type) {
        CodeTypeVo codeType = CodeTypeVo.getCodeType(type);
        log.info("根据codeType判断是否需要进行过滤条件");
        ISendFilterService sendFilterService = codeFilterMap.get(codeType);
        if(sendFilterService!=null){
            sendFilterService.doFilter(account);
        }
        log.info("生成验证码");
        String code = RandomStringUtils.randomNumeric(4);
        log.info("根据类型发送验证码");
        ISendMessageService iSendMessageService = codeSendMap.get(codeType);
        if(iSendMessageService==null){
            throw new BaseException(Constants.ResponseCode.UN_ERROR, "未知错误，请稍后再试");
        }
        iSendMessageService.sendMessage(account,code);
        log.info("验证码发送成功，保存到缓存中");
        userAccountRepository.putCacheCode(account,type,code);
    }

    @Override
    public void validCode(String flakeSnowId,String account, String code, String type) {
        //根据类型和账号从缓存中获取验证码

        //校验验证码

        //校验成功，执行绑定操作

    }
}
