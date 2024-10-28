package com.gaoyifeng.IDaaS.config;


import com.gaoyifeng.IDaaS.filter.HttpThreadLocalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {



    @Bean
    public FilterRegistrationBean<HttpThreadLocalFilter> customFilter() {
        FilterRegistrationBean<HttpThreadLocalFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpThreadLocalFilter());
        registrationBean.addUrlPatterns("/*"); // 指定过滤的 URL 匹配模式
        return registrationBean;
    }


}
