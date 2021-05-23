package com.dauhunon.Products;

import com.dauhunon.Brands.Brand;
import com.dauhunon.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity implements Serializable {
  @ManyToOne
  @JoinColumn(name="brand_id", nullable=false)
  private Brand brand;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String thumbnail;

  @Column(columnDefinition = "varchar(255) default ''")
  private String slug;

  @Column(nullable = false)
  private float price;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private int total;

  @Column(columnDefinition = "boolean default true")
  private boolean published;

  public Product(String name, String thumbnail, String slug, float price, String imageUrl, int total) {
    this.name = name;
    this.thumbnail = thumbnail;
    this.slug = slug;
    this.price = price;
    this.imageUrl = imageUrl;
    this.total = total;
  }
}
