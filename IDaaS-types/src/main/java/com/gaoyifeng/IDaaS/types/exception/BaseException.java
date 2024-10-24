package com.gaoyifeng.IDaaS.types.exception;


import com.gaoyifeng.IDaaS.types.commom.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常信息
     */
    private String message;


    /**
     * 异常描述
     */
    private String content;

    public BaseException(Constants.ResponseCode responseCode,String content){
        this.code = responseCode.getCode();
        this.message = responseCode.getInfo();
        this.content = content;
    }


}

