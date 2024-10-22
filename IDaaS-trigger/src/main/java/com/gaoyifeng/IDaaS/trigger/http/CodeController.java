package com.gaoyifeng.IDaaS.trigger.http;


import com.gaoyifeng.IDaaS.trigger.http.dto.CodeEntity;
import com.gaoyifeng.IDaaS.trigger.http.dto.ValidEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {


    @PostMapping("/getCode")
    public void getCode(@RequestBody CodeEntity codeEntity){

    }

    @PostMapping("/validCode")
    public void validCode(@RequestBody ValidEntity validEntity){

    }

}
