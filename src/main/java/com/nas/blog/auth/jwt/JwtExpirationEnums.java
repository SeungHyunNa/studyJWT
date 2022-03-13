package com.nas.blog.auth.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtExpirationEnums {
    ACCESS_TOKEN_EXPIRATION_TIME("JWT 토큰 만료기간 : 30분", 1000L * 60 * 30),
    REFRESH_TOKEN_EXPIRATION_TIME("REFRESH 토큰 만료기간 : 1일", 1000L * 60 * 60 * 24);

    private final String description;
    private final long value;

}
