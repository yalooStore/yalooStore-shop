package com.yaloostore.shop.role.common.converter;


import com.yaloostore.shop.role.common.RoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType roleType) {
        return roleType.getRoleName();
    }

    @Override
    public RoleType convertToEntityAttribute(String s) {
        return RoleType.stringToEnum(s);
    }
}
