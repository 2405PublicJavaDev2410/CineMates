package com.filmfellows.cinemates.common.config;

import com.filmfellows.cinemates.common.MemberProfileInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    private final MemberProfileInterceptor memberProfileInterceptor;

    public WebConfig(MemberProfileInterceptor memberProfileInterceptor) {
        this.memberProfileInterceptor = memberProfileInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 모든 요청에 대해 memberProfileInterceptor를 등록
        registry.addInterceptor(memberProfileInterceptor)
                .addPathPatterns("/**"); // 모든 경로에 대해 인터셉터를 적용
    }
}
