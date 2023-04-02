package com.yaloostore.shop.stock.repository.basic;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.stock.entity.BranchOffice;
import com.yaloostore.shop.stock.entity.BranchOfficeWarehousingOrder;
import com.yaloostore.shop.stock.repository.dummy.BranchOfficeDummy;
import com.yaloostore.shop.stock.repository.dummy.BranchOfficeStockDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WarehousingOrderRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    WarehousingOrderRepository warehousingOrderRepository;
    private BranchOfficeWarehousingOrder branchOfficeWarehousingOrder;

    @BeforeEach
    void setup(){


    }


    @DisplayName("분점 저장 테스트")
    @Test
    void testSave(){
        //given

        //when


    }





}