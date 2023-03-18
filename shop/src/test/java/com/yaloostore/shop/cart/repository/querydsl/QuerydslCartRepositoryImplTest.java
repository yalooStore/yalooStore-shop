package com.yaloostore.shop.cart.repository.querydsl;

import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class QuerydslCartRepositoryImplTest {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    protected QuerydslCartRepository cartRepository;

    private Product product;
    private Member member;


    @BeforeEach
    void setUp() {

        product= ProductDummy.dummy();
        member = MemberDummy.dummy();
    }


    @DisplayName("해당 회원의 해당 상품이 존재하는지 테스트 - 성공")
    @Test
    void isExist() {
        //given
        entityManager.persist(member);
        entityManager.persist(product);

        Cart cart = Cart.create(member, product);
        entityManager.persist(cart);

        //when
        Boolean result = cartRepository.queryExistCartByMemberIdAndProductId(member.getMemberId(), product.getProductId());

        //then
        assertThat(result).isTrue();


    }


    @DisplayName("회원 아이디로 장바구니를 가져오는 테스트")
    @Test
    void queryFindCartByMemberId() {

        //given
        entityManager.persist(member);
        entityManager.persist(product);

        Cart cart = Cart.create(member, product);
        entityManager.persist(cart);

        //when
        Page<Cart> result = cartRepository.queryFindCartByMemberId(member.getMemberId(), PageRequest.of(0, 5));


        //then
        assertThat(result.get()).isNotNull();
        assertThat(result.getNumberOfElements()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getProduct().getProductId()).isEqualTo(product.getProductId());
        assertThat(result.getContent().get(0).getMember().getMemberId()).isEqualTo(member.getMemberId());


    }
}