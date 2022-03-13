package com.nas.blog.auth.controller;

import com.nas.blog.auth.jwt.JwtHeaderUtilEnums;
import com.nas.blog.auth.jwt.JwtTokenUtil;
import com.nas.blog.dto.ResponseDto;
import com.nas.blog.dto.TokenDto;
import com.nas.blog.user.controller.form.JoinForm;
import com.nas.blog.entity.User;
import com.nas.blog.user.controller.form.LoginForm;
import com.nas.blog.auth.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/join")
    public ResponseDto save(@Validated @RequestBody JoinForm joinForm) {

        User user = getUser(joinForm);
        userService.Join(user);
        return ResponseDto.ok();
    }

    @PostMapping("/login")
    public ResponseDto<TokenDto> login(@Validated @RequestBody LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        TokenDto token = userService.login(loginForm);
        Cookie accessToken = jwtTokenUtil.createCookie(JwtHeaderUtilEnums.ACCESS_TOKEN_NAME.getValue(), token.getAccessToken());
        Cookie refreshToken = jwtTokenUtil.createCookie(JwtHeaderUtilEnums.REFRESH_TOKEN_NAME.getValue(), token.getRefreshToken());
        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            System.out.println("redirectUrl2 = " + redirectUrl);
        }

        return ResponseDto.ok(token);
    }

    private User getUser(JoinForm joinForm) {
        User user = User.builder()
                .userName(joinForm.getUsername())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .email(joinForm.getEmail())
                .role(joinForm.getRole())
                .build();
        return user;
    }

    @PostMapping("/logout")
    public ResponseDto logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie accessTokenCookie = jwtTokenUtil.getCookie(request, JwtHeaderUtilEnums.ACCESS_TOKEN_NAME.getValue());

        if (accessTokenCookie != null) {
            deleteRedisRefreshToken(accessTokenCookie);
        }

        SecurityContextHolder.clearContext();
        accessTokenCookie = jwtTokenUtil.removeCookie(JwtHeaderUtilEnums.ACCESS_TOKEN_NAME.getValue());
        Cookie refreshTokenCookie = jwtTokenUtil.removeCookie(JwtHeaderUtilEnums.REFRESH_TOKEN_NAME.getValue());

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseDto.ok();
    }

    private boolean deleteRedisRefreshToken(Cookie accessTokenCookie) {
        try {
            String accessToken = accessTokenCookie.getValue();
            String email = jwtTokenUtil.getEmail(accessToken);
            userService.logout(accessToken, email);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
