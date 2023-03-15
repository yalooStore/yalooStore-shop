package com.yaloostore.shop.order.repository.basic;

import com.yaloostore.shop.order.entity.MemberOrderCoupon;
import com.yaloostore.shop.order.entity.MemberOrderCoupon.MemberOrderCouponPk;
import com.yaloostore.shop.order.repository.common.CommonMemberOrderCouponRepository;
import org.springframework.data.repository.Repository;

public interface MemberOrderCouponRepository extends Repository<MemberOrderCoupon, MemberOrderCouponPk>, CommonMemberOrderCouponRepository {

}
