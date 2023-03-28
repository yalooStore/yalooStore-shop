package com.yaloostore.shop.auth.service.impl;

import com.yaloostore.shop.auth.service.inter.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.yaloostore.shop.auth.utils.AuthUtil.*;


/**
 * 로그아웃과 토큰 재발급 관련 처리 기능을 담담하는 서비스 구현체입니다.
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RedisTemplate<String, Object> redisTemplate;



    /**
     *  {@inheritDoc}
     *  */
    @Override
    public void logout(String uuid) {

        redisTemplate.opsForHash().delete(uuid, REFRESH_TOKEN.getValue());
        redisTemplate.opsForHash().delete(uuid, ACCESS_TOKEN.getValue());
        redisTemplate.opsForHash().delete(uuid, PRINCIPALS.getValue());
        redisTemplate.opsForHash().delete(uuid, USER_ID.getValue());

    }



    /**
     *  {@inheritDoc}
     *  */
    @Override
    public String getLoginId(String uuid) {

        String loginId = redisTemplate.opsForHash().get(uuid, USER_ID.getValue()).toString();

        return Objects.requireNonNull(loginId);
    }


    /**
     *  {@inheritDoc}
     *  */
    @Override
    public String getPrincipals(String uuid) {
        String principal = redisTemplate.opsForHash().get(uuid, PRINCIPALS.getValue()).toString();


        return Objects.requireNonNull(principal);
    }



    /**
     *  {@inheritDoc}
     *  */
    @Override
    public void reissue(String uuid, String accessToken) {

        redisTemplate.opsForHash().delete(uuid,ACCESS_TOKEN.getValue());
        redisTemplate.opsForHash().put(uuid, ACCESS_TOKEN.getValue(), accessToken);
    }
}
