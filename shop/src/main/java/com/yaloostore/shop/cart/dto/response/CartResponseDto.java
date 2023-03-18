package com.yaloostore.shop.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto {
    //product
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private Long rawPrice;
    private Long discountPrice;
    private Long discountPercent;

    //book
    private String publisherName;
    private String authorName;


//    /**
//     * cart entity를 dto 객체로 변환시켜주는 메소드입니다.
//     * */
//    public static CartResponseDto fromEntity(){
//        return CartResponseDto.builder().build();
//    }


}
