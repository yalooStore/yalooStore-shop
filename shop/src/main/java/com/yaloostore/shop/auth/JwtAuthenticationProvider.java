package com.yaloostore.shop.auth;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.response.AuthorizationMetaResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.ResponseDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final RestTemplate restTemplate;

    private final String authServerUrl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();


        try {
            HttpHeaders headers =new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", token);

            URI uri = UriComponentsBuilder.fromUriString(authServerUrl).pathSegment("authorizations").build().toUri();

            RequestEntity<Void> getAuthorization = new RequestEntity<>(
                    headers,
                    HttpMethod.GET,
                    uri
            );

            ResponseEntity<ResponseDto<AuthorizationMetaResponse>> responseEntity =
                    restTemplate.exchange(getAuthorization,
                            new ParameterizedTypeReference<>() {
                            });

            AuthorizationMetaResponse data = responseEntity.getBody().getData();

            return JwtAuthenticationToken.Authenticated(
                    token,
                    data.getName(),
                    data.getAuthorities()
            );

        } catch (RestClientException e){
            throw new BadCredentialsException("token is invalid!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
