package com.yaloostore.shop.member.repository.querydsl;

import com.yaloostore.shop.member.dto.response.MemberAddressResponse;
import com.yaloostore.shop.member.dummy.MemberAddressDummy;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberAddressRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class QuerydslMemberAddressRepositoryTest {


    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    QuerydslMemberAddressRepository querydslMemberAddressRepository;

    private Member member;

    private MemberAddress memberAddress;


    @BeforeEach
    void setUp() {
        member = MemberDummy.dummy();
        entityManager.persist(member);

        memberAddress = MemberAddressDummy.dummy(member);
        entityManager.persist(memberAddress);
    }

    @DisplayName("로그인할 때 사용하는 아이디로 배송지 리스트 찾아오기 - 한 회원이 여러개의 배송지를 가지고 있는 경우, 삭제되지 않은 경우만 가져온다.")
    @Test
    void testQueryMemberAddressFindAllByLoginId() {
        //given
        MemberAddress memberAddress2 = MemberAddressDummy.dummy2(member);


        entityManager.persist(memberAddress2);
        String loginId = member.getId();

        Member member2 = MemberDummy.dummy2();
        entityManager.persist(member2);

        MemberAddress memberAddress3 = MemberAddressDummy.dummy3(member2);
        entityManager.persist(memberAddress3);
        String member2Id = member2.getId();


        //when
        List<MemberAddressResponse> memberAddressResponseList = querydslMemberAddressRepository.queryMemberAddressFindAllByLoginId(loginId);
        List<MemberAddressResponse> memberAddressResponseList2 = querydslMemberAddressRepository.queryMemberAddressFindAllByLoginId(member2Id);

        //then
        assertThat(memberAddressResponseList.isEmpty()).isFalse();
        assertThat(memberAddressResponseList.size()).isEqualTo(2L);
        assertThat(memberAddressResponseList.get(0).getRecipientName()).isEqualTo(memberAddress.getRecipientName());


        assertThat(memberAddressResponseList2.isEmpty()).isFalse();
        assertThat(memberAddressResponseList2.size()).isOne();

    }

    @DisplayName("MemberAddressId를 사용해서 해당 배송지를 찾아오기 - 삭제되지 않은 경우에 한해서 가져온다.")
    @Test
    void testQueryMemberAddressFindByMemberAddressId() {
        //given
        Long memberAddressId = memberAddress.getMemberAddressId();

        //when
        Optional<MemberAddressResponse> opMemberAddress = querydslMemberAddressRepository.queryMemberAddressFindByMemberAddressId(memberAddressId);

        assertThat(opMemberAddress.isPresent()).isTrue();
        assertThat(opMemberAddress.get().getZipCode()).isEqualTo(memberAddress.getZipCode());
    }


    @DisplayName("memberAddressId와 로그인 시 사용하는 아이디로 해당 배송지 찾아오는 테스트 - 한 회원이 여러 배송지를 가지고 있을 경우 " +
            "memberAddressId는 유일한 키이기 때문에 이에 해당하는 삭제되지 않는 배송지만 가져온다.")
    @Test
    void testQueryMemberAddressFindByMemberAddressIdAndLoginId() {
        //given
        MemberAddress memberAddress2 = MemberAddressDummy.dummy2(member);
        entityManager.persist(memberAddress2);

        String loginId = member.getId();
        Long memberAddressId = memberAddress.getMemberAddressId();

        //when
        Optional<MemberAddressResponse> optionalMemberAddressResponse = querydslMemberAddressRepository.queryMemberAddressFindByMemberAddressIdAndLoginId(memberAddressId, loginId);

        //then
        assertThat(optionalMemberAddressResponse.isPresent());
        assertThat(optionalMemberAddressResponse.get().getRecipientPhoneNumber()).isNotEqualTo(memberAddress2.getRecipientPhoneNumber());


    }

    @DisplayName("회원이 등록한 배송지 개수가 맞는지 확인하는 테스트 - 회원은 배송지를 여러개 가질 수 있기 때문에 삭제되지 않은 경우 몇개인지를 가와준다.")
    @Test
    void countMemberAddressByLoginId() {

        //given
        MemberAddress memberAddress3 = MemberAddressDummy.dummy3(member);
        entityManager.persist(memberAddress3);

        String loginId = member.getId();

        //when
        long cnt = querydslMemberAddressRepository.countMemberAddressByLoginId(loginId);

        //then
        assertThat(cnt).isNotZero();
        assertThat(cnt).isEqualTo(2);


    }


    @DisplayName("로그인에 사용하는 id와 배송지 기본키를 이용해 해당 배송지가 존재하는지 확인 - 삭제되지 않고 입력받은 값에 해당하는 배송지를 반환")
    @Test
    void existByLoginIdAndMemberAddressId() {
        //given
        MemberAddress memberAddress3 = MemberAddressDummy.dummy3(member);
        entityManager.persist(memberAddress3);

        String loginId = member.getId();
        Long memberAddressId = memberAddress.getMemberAddressId();

        //when
        boolean trueResult = querydslMemberAddressRepository.existByLoginIdAndMemberAddressId(memberAddressId, loginId);

        boolean falseResult = querydslMemberAddressRepository.existByLoginIdAndMemberAddressId(4L, "false");

        //then
        assertThat(trueResult).isTrue();
        assertThat(falseResult).isFalse();


    }
}