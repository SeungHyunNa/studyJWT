package com.nas.blog.auth.service;

import com.nas.blog.auth.PrincipalDetail;
import com.nas.blog.auth.repository.LogoutAccessTokenRedisRepository;
import com.nas.blog.auth.repository.RefreshTokenRedisRepository;
import com.nas.blog.auth.token.LogoutAccessToken;
import com.nas.blog.auth.token.RefreshToken;
import com.nas.blog.auth.jwt.JwtExpirationEnums;
import com.nas.blog.auth.jwt.JwtTokenUtil;
import com.nas.blog.config.CacheKey;
import com.nas.blog.dto.TokenDto;
import com.nas.blog.entity.User;
import com.nas.blog.exception.FieldException;
import com.nas.blog.user.controller.form.LoginForm;
import com.nas.blog.user.repository.UserRepository;
import com.nas.blog.util.constant.FieldConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nas.blog.exception.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenUtil jwtTokenUtil;

    public void Join(User user) {
        existUserEmail(user);
        userRepository.save(user);
    }

    public TokenDto login(LoginForm loginForm) {
        // Login EMAIL/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginForm.toAuthentication();

        // 검증
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();

        String email = principalDetail.getEmail();
        String accessToken = jwtTokenUtil.generateAccessToken(email);
        RefreshToken refreshToken = saveRefreshToken(email);

        return TokenDto.of(accessToken, refreshToken.getRefreshToken());

    }

    @CacheEvict(value = CacheKey.USER, key = "#email") // 어노테이션에 설정된 key로 캐시에 저장된 데이터를 삭제합니다.
    public void logout(String accessToken, String email) {
        refreshTokenRedisRepository.deleteById(email);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(accessToken, email, jwtTokenUtil.getExpiration(accessToken)));
    }

    private void existUserEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new FieldException(DUPLICATE_EMAIL, FieldConstant.EMAIL);
        }
    }

    private RefreshToken saveRefreshToken(String email) {
        String refreshToken = jwtTokenUtil.generateRefreshToken(email);
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(email, refreshToken, JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
    }
}
