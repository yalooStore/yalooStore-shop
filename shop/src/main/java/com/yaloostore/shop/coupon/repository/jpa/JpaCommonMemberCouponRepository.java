package com.yaloostore.shop.coupon.repository.jpa;

import com.yaloostore.shop.coupon.entity.MemberCoupon;
import com.yaloostore.shop.coupon.repository.common.CommonMemberCouponRepository;
import org.springframework.data.repository.Repository;

public interface JpaCommonMemberCouponRepository extends Repository<MemberCoupon, Long>, CommonMemberCouponRepository {

}
