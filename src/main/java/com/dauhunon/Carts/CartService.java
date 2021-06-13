package com.dauhunon.Carts;

import com.dauhunon.Brands.Brand;
import com.dauhunon.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
  private final CartRepo cartRepo;

  @Autowired
  public CartService(CartRepo cartRepo) {
    this.cartRepo = cartRepo;
  }

  public Page<Cart> listAll(long userId, int currentPage){
    Sort sort = Sort.by("createdAt");
    sort = sort.descending();

    Pageable page = PageRequest.of(currentPage-1, 10, sort);
    return cartRepo.listAll(userId, page);
  }
  
  public Cart getPendingCart(Long userId) {
    List<Cart> carts = cartRepo.listByStatus(userId, Const.PENDING);
    if(carts != null && carts.size() > 0) {
      return carts.get(0);
    }
    throw new IllegalStateException("Cart non exists");
  }

  public void checkout(Long userId) {
    Cart cart = getPendingCart(userId);
    cart.setStatus(Const.IN_PROCESS);
    cartRepo.save(cart);
  }
}
