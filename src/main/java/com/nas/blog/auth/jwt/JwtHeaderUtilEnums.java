package com.nas.blog.auth.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtHeaderUtilEnums {
    // LocalStorage 방식
    // 클라이언트 쪽에서 jwt 토큰을 반환받고 LocalStorage에 저장 -> XSS공격에 취약 스크립트로 쉽게 토큰을 탈취 할 수 있다.
    // 이후 인증시 헤더에 jwt토큰 정보를 담아서 요청 -> CSRF공격에는 방어
    GRANT_TYPE("JWT 타입 / Bearer ", "Bearer "),
    AUTHORIZATION_HEADER("JWT 인증 헤더", "Authorization"),

    // Cookie에 JWT를 저장할 경우
    // Cookie에는 HttpOnly라는 옵션이 존재하는데 이 옵션을 지정하면 Script에서 Cookie를 읽어올 수 없게한다.  -> XSS공격에 방어
    // 서버측에서 Cookie의 정보로 인증하기 때문에 리퀘스트 요청시 헤더에 토큰을 설정하지 않음 ->  CSRF공격에 취약
    // Cookie Referer Check등으로 대부분의 CSRF공격에 방어가능하는등 보완방법이 있음 -> Cookie방식을 더 선호하는 이유
    // Cookie Referer Check : 요청을 보내면 요청을 보낸 Domain을 알 수 있는데
    // 이 Domain이 내가 허용한 Domain에서 온 요청인지 체크하면 된다. 일반적으로 Referer Check로만 대부분의 CSRF를 방어할 수 있다.
    ACCESS_TOKEN_NAME("JWT 토큰 이름", "accessToken"),
    REFRESH_TOKEN_NAME("JWT REFRESH 토큰 이름", "refreshToken");

    private final String description;
    private final String value;
}
