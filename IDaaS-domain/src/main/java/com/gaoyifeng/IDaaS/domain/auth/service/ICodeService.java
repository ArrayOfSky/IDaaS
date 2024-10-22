package com.gaoyifeng.IDaaS.domain.auth.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICodeService {

    void getCode(String account, String type);
}
