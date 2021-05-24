package com.dauhunon.Products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepo extends
  JpaRepository<Product, Long>,
  PagingAndSortingRepository<Product, Long> {

  @Query("select p from Product p where concat(name, thumbnail, price, p.brand.name) like %?1%")
  public Page<Product> listAll(String keyword, Pageable page);

  @Query("select p from Product p where concat(name, thumbnail, price, p.brand.name) like %?1% and published = ?2")
  public Page<Product> listAllWherePublished(String keyword, boolean published, Pageable page);
}
