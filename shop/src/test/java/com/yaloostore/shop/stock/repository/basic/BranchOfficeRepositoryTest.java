package com.yaloostore.shop.stock.repository.basic;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeStock;
import com.yaloostore.shop.stock.repository.dummy.BranchOfficeDummy;
import com.yaloostore.shop.stock.repository.dummy.BranchOfficeStockDummy;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BranchOfficeRepositoryTest {


    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    BranchOfficeRepository branchOfficeRepository;
    private BranchOffice branchOffice;
    private BranchOfficeStock branchOfficeStock;
    private Product product;


    @BeforeEach
    void setup(){

        product = ProductDummy.dummy();
        testEntityManager.persist(product);

        branchOfficeStock = BranchOfficeStockDummy.dummy(product);
        testEntityManager.persist(branchOfficeStock);

        branchOffice = BranchOfficeDummy.dummy(branchOfficeStock);

    }


    @DisplayName("분점 저장 테스트")
    @Test
    void testSave(){
        //given
        testEntityManager.persist(branchOffice);

        //when
        BranchOffice saved = branchOfficeRepository.save(branchOffice);

        //then
        assertThat(saved.getIsDeleted()).isFalse();
        assertThat(saved.getBranchOfficeId()).isEqualTo(branchOffice.getBranchOfficeId());

    }

    @DisplayName("기본키로 해당하는 분점 찾아오기")
    @Test
    void testFindById(){
        //given
        testEntityManager.persist(branchOffice);
        Long id = branchOffice.getBranchOfficeId();


        //when
        BranchOffice result = branchOfficeRepository.findByBranchOfficeId(id);

        //then
        assertThat(result.getBranchOfficeId()).isEqualTo(id);
        assertThat(result.getIsDeleted()).isFalse();


    }

}