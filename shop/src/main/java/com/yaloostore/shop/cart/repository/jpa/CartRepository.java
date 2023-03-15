package com.yaloostore.shop.cart.repository.jpa;

import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.entity.Cart.Pk;
import com.yaloostore.shop.cart.repository.common.CommonCartRepository;
import org.springframework.data.repository.Repository;

public interface CartRepository extends Repository<Cart, Pk>,CommonCartRepository {




}
