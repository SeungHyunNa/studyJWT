package com.nas.blog.config;

import lombok.Getter;

@Getter
public class CacheKey {

    public static final String USER = "user";
    public static final int DEFAULT_EXPIRE_SEC = 60 * 3;
}
