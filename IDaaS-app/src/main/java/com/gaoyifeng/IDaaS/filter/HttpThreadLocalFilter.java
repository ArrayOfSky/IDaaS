package com.gaoyifeng.IDaaS.filter;


import com.gaoyifeng.IDaaS.types.utils.HttpThreadLocalUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class HttpThreadLocalFilter  extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("进入了HttpThreadLocalFilter");
        try {
            HttpThreadLocalUtil.setRequest(request);
            HttpThreadLocalUtil.setResponse(response);
            // 执行过滤器链
            filterChain.doFilter(request, response);
        } finally {
            // 清理 ThreadLocal 变量，防止内存泄漏
            HttpThreadLocalUtil.clear();
        }

    }


}
