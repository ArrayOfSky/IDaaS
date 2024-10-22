package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.CodeService;
import com.gaoyifeng.IDaaS.trigger.http.dto.CodeEntity;
import com.gaoyifeng.IDaaS.trigger.http.dto.ValidEntity;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.model.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Resource
    private ICodeService codeService;

    @PostMapping("/getCode")
    public Response<String> getCode(@RequestBody CodeEntity codeEntity){
        String account = codeEntity.getAccount();
        String type = codeEntity.getType();
        codeService.getCode(account,type);
        return Response.<String>builder()
                .code(Constants.ResponseCode.SUCCESS.getCode())
                .info(Constants.ResponseCode.SUCCESS.getInfo())
                .data("")
                .build();
    }

    @PostMapping("/validCode")
    public void validCode(@RequestBody ValidEntity validEntity){
        validEntity.getAccount();
        validEntity.getCode();
        validEntity.getType();
    }

}
