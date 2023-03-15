package com.yaloostore.shop.cart.repository.basic;

import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CartCommonRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CartCommonRepository cartCommonRepository;


    private Member member;
    private Product product;

    @BeforeEach
    void setUp() {
        member = MemberDummy.dummy();
        product = ProductDummy.dummy();

        entityManager.persist(member);
        entityManager.persist(product);
    }



    @DisplayName("카트 저장 테스트")
    @Test
    void testSave(){

        //given
        Cart cart = Cart.create(member, product);
        entityManager.persist(cart);

        //when
        Cart savedCart = cartCommonRepository.save(cart);

        //then
        assertThat(savedCart.getProduct().getProductId()).isEqualTo(product.getProductId());
        assertThat(savedCart.getMember().getMemberId()).isEqualTo(member.getMemberId());


    }
    @Test
    void testFindByMemberIdAndProductId(){

        //given
        Cart cart = Cart.create(member, product);
        entityManager.persist(cart);

        //when
        Optional<Cart> opCart = cartCommonRepository.findByMember_MemberIdAndProduct_ProductId(member.getMemberId(), product.getProductId());

        //then
        assertThat(opCart.isPresent());
        assertThat(opCart.get().getMember().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(opCart.get().getProduct().getProductId()).isEqualTo(member.getMemberId());

    }
    @Test
    void testDeleteByMemberIdAndProductId(){
        //given
        Cart cart = Cart.create(member, product);

        //when
        cartCommonRepository.deleteByMember_MemberIdAndProduct_ProductId(member.getMemberId(), product.getProductId());

        //then
        assertThat(cartCommonRepository.findByMember_MemberIdAndProduct_ProductId(member.getMemberId(), product.getProductId())).isNotPresent();


    }
}