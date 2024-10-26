package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.domain.auth.service.IAuthService;
import com.gaoyifeng.IDaaS.trigger.http.dto.AuthEntity;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import com.gaoyifeng.IDaaS.types.model.Response;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

    @Resource
    IAuthService authService;

    @PostMapping("/verify")
    public void verify(@PathParam("token") String token){

    }

    @PostMapping("/authorize")
    public Response<String> login(@RequestBody AuthEntity authEntity) {
        try{
            log.info("准备进行验证");
            String account = authEntity.getAccount();
            String password = authEntity.getPassword();
            String token = authService.login(account,password);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(token)
                    .build();
        }catch (BaseException e){
            log.error("验证失败",e);
            return Response.<String>builder()
                    .code(e.getCode())
                    .info(e.getMessage())
                    .data(e.getContent())
                    .build();
        }catch (Exception e){
            log.error("验证失败",e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info("验证失败")
                    .data("验证失败")
                    .build();
        }
    }

}
