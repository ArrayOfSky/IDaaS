package com.gaoyifeng.IDaaS.domain.auth.service;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.CodeSendEntity;

/**
 * @author gaoyifeng
 */
public interface ICodeService {

    void getCode(String account, String type);

    void validCode(String flakeSnowId,String account, String code, String type);

    void sendCode(CodeSendEntity codeSendEntity);

}
