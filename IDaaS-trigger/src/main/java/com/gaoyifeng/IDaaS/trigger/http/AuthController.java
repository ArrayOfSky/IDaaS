package com.gaoyifeng.IDaaS.trigger.http;


import com.alibaba.fastjson2.JSON;
import com.gaoyifeng.IDaaS.domain.auth.service.IAuthService;
import com.gaoyifeng.IDaaS.trigger.http.dto.AuthEntity;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import com.gaoyifeng.IDaaS.types.model.Response;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    IAuthService authService;

    @PostMapping("/renewval")
    public Response<String> renewval(@RequestHeader("token") String token,
                                     @RequestHeader("refresh_token")String refreshToken){

        try{
            Map map = authService.renewval(token,refreshToken);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(JSON.toJSONString(map))
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


    @PostMapping("/verify")
    public Response<String> verify(@RequestHeader("token") String token){
        try{
            Map map = authService.verify(token);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(JSON.toJSONString(map))
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

    @PostMapping("/authorize")
    public Response<String> login(@RequestBody AuthEntity authEntity) {
        try{
            log.info("准备进行验证");
            String account = authEntity.getAccount();
            String password = authEntity.getPassword();
            String type = authEntity.getType();
            String token = authService.login(account,password,type);
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
