package com.yaloostore.shop.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * pagination(paging)를 사용해 데이터를 나눠서 넘겨줄 때 사용하는 dto 객체입니다.
 * 공통으로 paginatione된 정보를 넘길 때 해당 response dto를 사용해 처리해줍니다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDto<T> {

    private long totalPage;
    private long currentPage;
    private long totalDataCount;
    private List<T> dataList;
}
