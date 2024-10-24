package com.gaoyifeng.IDaaS.domain.auth.model.valobj;

import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CodeTypeVo {

    BOUND_EMAIL("0","邮箱认证"),
    BOUND_PHONE("1","手机号认证"),
    BOUND_WECHAT("2","微信公众号认证");

    private String code;
    private String desc;

    public static CodeTypeVo getCodeType(String type){
        //根据type与code对应，返回对应的常量对象
        for (CodeTypeVo codeType : CodeTypeVo.values()) {
            if (codeType.code.equals(type)) {
                return codeType;
            }
        }
         throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER,"Invalid type: " + type);
    }


}
