package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.domain.auth.model.valobj.CodeTypeVo;
import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import com.gaoyifeng.IDaaS.trigger.http.dto.CodeEntity;
import com.gaoyifeng.IDaaS.trigger.http.dto.ValidEntity;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import com.gaoyifeng.IDaaS.types.model.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 验证码
 * @author gaoyifeng
 */
@RestController
@RequestMapping("/code")
@Slf4j
public class CodeController {

    @Resource
    private ICodeService codeService;

    /**
     * 获取验证码类型字典
     */
    @PostMapping("/getCodeType")
    public Response<Map<String,String>> getCodeType(){
        return Response.<Map<String,String>>builder()
                .code(Constants.ResponseCode.SUCCESS.getCode())
                .info(Constants.ResponseCode.SUCCESS.getInfo())
                .data(CodeTypeVo.toJson())
                .build();
    }


    /**
     * 获取验证码
     */
    @PostMapping("/getCode")
    public Response<String> getCode(@RequestBody CodeEntity codeEntity){
        try{
            log.info("准备发送验证码");
            String account = codeEntity.getAccount();
            String type = codeEntity.getType();
            codeService.getCode(account,type);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data("")
                    .build();
        }catch (BaseException e){
            log.error("生成验证码失败",e);
            return Response.<String>builder()
                    .code(e.getCode())
                    .info(e.getMessage())
                    .data(e.getContent())
                    .build();
        }catch (Exception e){
            log.error("生成验证码失败",e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info("生成验证码失败")
                    .data("发送验证码失败")
                    .build();
        }
    }

    /**
     * 验证验证码，完成绑定邮箱、手机号操作
     */
    @PostMapping("/validCode")
    public Response<String> validCode(@RequestBody ValidEntity validEntity){
        try {
            String flakeSnowId = validEntity.getFlakeSnowId();
            String account = validEntity.getAccount();
            String code = validEntity.getCode();
            String type = validEntity.getType();
            codeService.validCode(flakeSnowId,account,code,type);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data("")
                    .build();
        }catch (BaseException e){
            log.error("验证验证码失败",e);
            return Response.<String>builder()
                    .code(e.getCode())
                    .info(e.getMessage())
                    .data(e.getContent())
                    .build();
        }catch (Exception e){
            log.error("验证验证码失败",e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info("验证验证码失败")
                    .data("验证验证码失败")
                    .build();
        }
    }

}
