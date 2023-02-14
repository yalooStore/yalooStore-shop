package com.yaloostore.shop.member.common.converter;



import com.yaloostore.shop.member.common.GenderCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 컨버터를 사용해서 해당 회원 성별 코드를 Integer 타입으로 변환해서 넣어준다.
 * */
@Converter(autoApply = true)
public class GenderCodeConverter implements AttributeConverter<GenderCode, Integer> {


    @Override
    public Integer convertToDatabaseColumn(GenderCode genderCode) {
        return genderCode.getGenderCode();
    }

    @Override
    public GenderCode convertToEntityAttribute(Integer genderCode) {
        if(Objects.isNull(genderCode)){
            return null;
        }
        return Stream.of(GenderCode.values())
                .filter(g -> genderCode.equals(g.getGenderCode())).findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
