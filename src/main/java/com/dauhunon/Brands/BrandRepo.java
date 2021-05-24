package com.dauhunon.Brands;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BrandRepo extends
  JpaRepository<Brand, Long>,
  PagingAndSortingRepository<Brand, Long> {

  @Query("select b from Brand b where concat(name, thumbnail) like %?1%")
  public Page<Brand> listAll(String keyword, Pageable page);

  @Query("select b from Brand b where concat(name, thumbnail) like %?1% and published = ?2")
  public Page<Brand> listAllWherePublished(String keyword, boolean published, Pageable page);

  @Query("select b from Brand b where lower(b.name) = ?1")
  public Brand findByName(String name);
}
