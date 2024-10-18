package com.gaoyifeng.IDaaS.controller;


import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaoyifeng
 */
@RestController("/oaa")
public class OaaController {


    @PostMapping("/verify")
    public void verify(@PathParam("token") String token){

    }

    @PostMapping("/authorize")
    public void login(String account,String password,String type){

    }


}
