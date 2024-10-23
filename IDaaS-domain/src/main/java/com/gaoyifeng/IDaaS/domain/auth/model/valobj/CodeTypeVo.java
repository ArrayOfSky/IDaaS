package com.gaoyifeng.IDaaS.domain.auth.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CodeTypeVo {

    EMAIL("0","邮箱认证"),
    PHONE("1","手机号认证"),
    WECHAT("2","微信公众号认证");

    private String code;
    private String desc;

}
