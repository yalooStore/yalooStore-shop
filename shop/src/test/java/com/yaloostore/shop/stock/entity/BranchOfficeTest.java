package com.yaloostore.shop.stock.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchOfficeTest {


    @DisplayName("점포 삭제 시 테스트")
    @Test
    void softDelete() {

        BranchOffice branchOffice = BranchOffice.builder()
                .branchOfficeName("dd")
                .branchOfficeTel("dd")
                .branchOfficeAddress("dd")
                .isDeleted(false)
                .build();

        branchOffice=branchOffice.softDelete();

        Assertions.assertThat(branchOffice.getIsDeleted()).isTrue();

    }
}