package com.filmfellows.cinemates.common;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class AuthenticationFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("필터 생성");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        String uri = req.getRequestURI();

        // 관리자 로그인창은 제외
        if(uri.startsWith("/admin/login")) {
            chain.doFilter(req, res);
            return;
        }

        // 필터 걸어야 하는 경로
        String[] protectedPaths = {"/admin/", "/my-page/", "/Ticketing/", "/chat/"};

        for(String path : protectedPaths) {
            if(uri.startsWith(path)) {
                String role = (String) session.getAttribute("role");

                if(role == null) {
                    res.sendRedirect(req.getContextPath() + "/login");
                    return;
                }

                if(path.equals("/admin/") && !role.equals("ADMIN")) {
                    res.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            }
        }
        chain.doFilter(req, res);
    }

    public void destroy() {
        Filter.super.destroy();
        log.info("필터 파괴");
    }

}
