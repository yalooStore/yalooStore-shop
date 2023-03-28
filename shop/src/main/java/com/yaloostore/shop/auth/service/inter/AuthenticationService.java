package com.yaloostore.shop.auth.service.inter;

public interface AuthenticationService {



    /**
     * 로그인한 사용자가 로그아웃 하려할 때 사용자가 가진 uuid를 사용해서 redis에 저장된 회원 정보를 삭제하는 메소드입니다.
     *
     * @param uuid 로그인한 사용자가 가진 고유한 uuid
     * */
    void logout(String uuid);

    /**
     * 로그인한 사용자가 가진 uuid를 사용해서 redis에 저장된 회원 로그인 아이디를 가져오기 위한 메소드입니다.
     *
     * @param uuid 로그인한 사용자가 가진 고유한 uuid
     * */
    String getLoginId(String uuid);

    /**
     * 로그인한 사용자가 가진 uuid를 사용해서 redis에 저장된 회원 권한 정보를 가져오기 위한 메소드입니다.
     *
     * @param uuid 로그인한 사용자가 가진 고유한 uuid
     * */
    String getPrincipals(String uuid);

    /**
     * JWT 토큰 재발급 이후 처리를 위한 기능으로 redis에서 해당 유저의 정보를 갱신하기 위한 메소드입니다.
     *
     * @param uuid 로그인한 사용자가 가진 고유한 uuid
     * */
    void reissue(String uuid, String accessToken);
}
