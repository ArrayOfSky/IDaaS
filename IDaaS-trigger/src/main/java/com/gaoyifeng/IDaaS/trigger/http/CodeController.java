package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.CodeService;
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

@RestController
@RequestMapping("/code")
@Slf4j
public class CodeController {

    @Resource
    private ICodeService codeService;

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
            e.printStackTrace();
            System.out.println("11111111111111111");
            log.error("生成验证码失败",e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info("生成验证码失败")
                    .data("发送验证码失败")
                    .build();
        }
    }

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
        } catch (Exception e) {
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info("验证失败")
                    .data("")
                    .build();
        }
    }

}
