package com.yaloostore.shop.member.entity;


import ch.qos.logback.core.net.server.Client;
import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.role.exception.AlreadyDeletedAddressException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="member_addresses")
public class MemberAddress {

    @Id
    @Column(name = "member_address_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "recipient_name",nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone_number",nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "zip_code",nullable = false)
    private Integer zipCode;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "detailed_address", nullable = true)
    private String detailedAddress;


    @Column(name = "is_default_address", nullable = false)
    private boolean isDefaultAddress;

    @Column(name = "is_deleted_address", nullable = false)
    private boolean isDeletedAddress;


    /**
     * 기본 배송지 지정 메소드
     * */
    public void makeADefaultAddress(){
        this.isDefaultAddress = true;
    }

    /**
     * 배송지 삭제 메소드
     * */
    public void deleteAddress(){
        if(this.isDeletedAddress) {
            throw new ClientException(ErrorCode.MEMBER_ALREADY_DELETED,
                    "member address is already deleted" + this.memberAddressId)
        }
        this.isDefaultAddress = true;
    }



}
