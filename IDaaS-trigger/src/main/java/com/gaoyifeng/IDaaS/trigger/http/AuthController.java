package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.trigger.http.dto.AuthEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @PostMapping("/verify")
    public void verify(@PathParam("token") String token){

    }

    @PostMapping("/authorize")
    public void login(@RequestBody AuthEntity authEntity) {

    }

}
