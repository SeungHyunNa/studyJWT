package com.nas.blog.auth.jwt;

import com.nas.blog.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티 필터를 OncePerRequestFilter JwtFilter로 커스터마이징
// JWT 토큰 검사를 한 Request당 한번만 검사하고 싶기때문에 OncePerRequestFilter를 상속
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final PrincipalDetailService principalDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null){
            String redirectUrl = savedRequest.getRedirectUrl();
            System.out.println("redirectUrl2 = " + redirectUrl);
        }

        String accessToken = resolveToken(request);

        if (accessToken != null && jwtTokenUtil.validateToken(accessToken)) {
            setAuthentication(accessToken);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie cookie = jwtTokenUtil.getCookie(request, JwtHeaderUtilEnums.ACCESS_TOKEN_NAME.getValue());
        if (cookie == null){
            return null;
        }
        String token = cookie.getValue();
        return token;
    }

    private void setAuthentication(String accessToken) {
        String email = jwtTokenUtil.getEmail(accessToken);
        UserDetails userDetails = principalDetailService.loadUserByUsername(email);
        SecurityContextHolder.getContext().setAuthentication(jwtTokenUtil.getAuthentication(userDetails));
    }

    private void checkLogout(String accessToken){

    }
}
