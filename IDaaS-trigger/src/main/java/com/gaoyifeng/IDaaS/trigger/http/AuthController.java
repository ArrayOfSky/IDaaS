package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.domain.auth.service.IAuthService;
import com.gaoyifeng.IDaaS.trigger.http.dto.AuthEntity;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import com.gaoyifeng.IDaaS.types.model.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户授权
 * @author gaoyifeng
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    IAuthService authService;

    /**
     * 使用refresh_token 刷新token
     */
    @PostMapping("/renewval")
    public Response<String> renewval(@RequestHeader("Authorization") String token,
                                     @RequestHeader("refresh-token")String refreshToken){
        try{
            authService.renewval(token,refreshToken);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data("")
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


    /**
     * 验证token是否有效,如果即将到期自动续约
     */
    @PostMapping("/verify")
    public Response<Map<String,String>> verify(@RequestHeader("Authorization") String token){
        try{
            Map<String,String> map = authService.verify(token);
            return Response.<Map<String,String>>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(map)
                    .build();
        }catch (BaseException e){
            log.error("验证失败",e);
            return Response.<Map<String,String>>builder()
                    .code(e.getCode())
                    .info(e.getMessage())
                    .data(null)
                    .build();
        }catch (Exception e){
            log.error("验证失败",e);
            return Response.<Map<String,String>>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info("验证失败")
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/authorize")
    public Response login(@RequestBody AuthEntity authEntity) {
        try{
            log.info("准备进行验证");
            String account = authEntity.getAccount();
            String password = authEntity.getPassword();
            String type = authEntity.getType();
            authService.login(account,password,type);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data("")
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
