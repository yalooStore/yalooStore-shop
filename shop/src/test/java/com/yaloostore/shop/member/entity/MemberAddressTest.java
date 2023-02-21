package com.yaloostore.shop.member.entity;

import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.role.exception.AlreadyDeletedAddressException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberAddressTest {


    @DisplayName("기본 배송지로 만드는 테스트")
    @Test
    void makeADefaultAddress() {

        MemberAddress memberAddress =
                MemberAddress.builder()
                        .isDefaultAddress(false)
                        .build();

        memberAddress.makeADefaultAddress();

        assertThat(memberAddress.isDefaultAddress()).isTrue();
    }


    @DisplayName("주소 삭제 테스트")
    @Test
    void deleteAddress() {
        //given

        MemberAddress memberAddress = MemberAddress.builder()
                .isDeletedAddress(false)
                .build();

        //when
        memberAddress.deleteAddress();

        //then
        assertThat(memberAddress.isDeletedAddress()).isTrue();
    }


    @DisplayName("이미 삭제된 주소 테스트 - AlreadyDeletedAddressException 예외를 던짐")
    @Test
    void fail_test_alreadyDeletedAddressException(){
        //given
        MemberAddress memberAddress = MemberAddress.builder()
                .isDeletedAddress(true)
                .build();

        //when, then
        assertThatThrownBy(()-> memberAddress.deleteAddress()).isInstanceOf(ClientException.class);


    }
}