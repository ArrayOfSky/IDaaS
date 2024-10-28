package com.gaoyifeng.IDaaS.filter;


import com.gaoyifeng.IDaaS.types.utils.HttpThreadLocalUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class HttpThreadLocalFilter  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进入了HttpThreadLocalFilter");
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            HttpThreadLocalUtil.setRequest(request);
            HttpThreadLocalUtil.setResponse(response);
            // 执行过滤器链
            filterChain.doFilter(request, response);
        } finally {
            // 清理 ThreadLocal 变量，防止内存泄漏
            HttpThreadLocalUtil.clear();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
