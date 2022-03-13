package com.nas.blog.auth.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("refreshToken") // 어노테이션의 선언된 value로 Redis의 Set 자료구조를 통해 해당 객체가 저장
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive // 설정한 시간 만큼 데이터를 저장합니다. 설정한 시간이 지나면 자동으로 해당 데이터가 사라지는 휘발 역할을 해줍니다.
    private Long expiration;

    public static RefreshToken createRefreshToken(String email, String refreshToken, Long expiration) {
        return RefreshToken.builder()
                .id(email)
                .refreshToken(refreshToken)
                .expiration(expiration / 1000)
                .build();
    }
}
