package com.yaloostore.shop.member.repository.basicRepository;

import com.yaloostore.shop.member.dummy.MemberAddressDummy;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberAddressRepositoryTest {

    @Autowired
    MemberAddressRepository repository;


    @Autowired
    TestEntityManager testEntityManager;


    private Member member;

    private MemberAddress memberAddress;


    @BeforeEach
    void setUp() {
        member = MemberDummy.dummy();
        testEntityManager.persist(member);

        memberAddress = MemberAddressDummy.dummy(member);
    }


    @DisplayName("회원 배송저 저장 테스트")
    @Test
    void testSave(){
        //given
        MemberAddress persist = testEntityManager.persist(memberAddress);

        //when
        MemberAddress savedMemberAddress = repository.save(persist);

        //then
        assertThat(savedMemberAddress.getZipCode()).isEqualTo(persist.getZipCode());

    }

    @DisplayName("회원 주소 id 값으로 회원주소를 찾는 테스트 - 회원 id(Long type)값으로 찾으면 리스트지만 이건 x")
    @Test
    void findById(){

        //given
        MemberAddress persist = testEntityManager.persist(memberAddress);

        //when
        Optional<MemberAddress> optionalMemberAddress = repository.findById(persist.getMemberAddressId());

        //then
        assertThat(optionalMemberAddress.isPresent()).isTrue();
        assertThat(optionalMemberAddress.get().getRecipientName()).isEqualTo(persist.getRecipientName());

    }


    @DisplayName("회원배송지 id로 배송지를 검색한 뒤 그에 해당하는 배송지 삭제 테스트")
    @Test
    void testDeleteById(){
        //given
        MemberAddress persist = testEntityManager.persist(memberAddress);

        //when
        repository.deleteById(persist.getMemberAddressId());

        Optional<MemberAddress> optionalMemberAddress = repository.findById(persist.getMemberAddressId());

        //then
        assertThat(optionalMemberAddress.isPresent()).isFalse();
        assertThat(optionalMemberAddress.isEmpty()).isTrue();

    }



}