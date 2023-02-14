package com.yaloostore.shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * rest template 세부 설정과 빈등록 클래스
 * */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(30000);
        factory.setReadTimeout(100000);
        factory.setBufferRequestBody(false);

        return factory;

    }



}
