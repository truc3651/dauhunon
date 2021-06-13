package com.dauhunon.Carts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CartRepo extends
  JpaRepository<Cart, Long>,
  PagingAndSortingRepository<Cart, Long> {

  @Query("select c from Cart c where c.user.id = ?1 and c.status = ?2")
  public List<Cart> listByStatus(long userId, String status);

  @Query("select c from Cart c where c.user.id = ?1")
  public Page<Cart> listAll(long userId, Pageable page);
}
