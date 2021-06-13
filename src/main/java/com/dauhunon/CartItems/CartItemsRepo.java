package com.dauhunon.CartItems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemsRepo extends JpaRepository<CartItems, Long> {
  @Query("select c from CartItems c where c.cart.user.id = ?1 and c.cart.status = 'PENDING' and c.product.id = ?2")
  public List<CartItems> getPendingCartItem(long userId, long productId);
}
