package com.yaloostore.shop.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.annotations.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * 스프링이 제공하는 캐시를 사용하기 위한 캐시 관련 설정 파일입니다.
 * */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private final RedisConnectionFactory redisConnectionFactory;
    private final ObjectMapper objectMapper;

    /**
     * cacheManager 빈등록을 위한 메서드입니다.
     * 분산 서버에서의 캐시 사용엔 외부 프로바이더를 사용하는 것이 권장되기 때문에 Redis를 사용하여 cacheManager를 구성합니다.
     * */
    @Bean
    public CacheManager redisCacheManager(){

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith("Cache: ") // 캐시 이름 앞에 붙일 문자열
                .entryTtl(Duration.ofMinutes(30)) // 캐시 항목 만료 시간 설정
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 캐시 키 직렬화 방법
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(springCacheRedisSerializer())); // 캐시 값 직렬화 방법


        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }

    private RedisSerializer<Object> springCacheRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }


}
