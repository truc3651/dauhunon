package com.dauhunon.Products;

import com.dauhunon.Brands.Brand;
import com.dauhunon.common.BaseEntity;
import com.dauhunon.utils.App;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity implements Serializable {
  @ManyToOne(optional = false)
  @JoinColumn(name="brand_id", referencedColumnName = "id")
  @JsonIgnore
  private Brand brand;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String thumbnail;

  private String slug;

  @Column(nullable = false)
  private float price;

  private String imageUrl;

  @Column(nullable = false)
  private int total;

  @Column(columnDefinition = "float default 0")
  private float discount;

  @Column(columnDefinition = "boolean default true")
  private boolean published;

  @Transient
  public String getPublicId() {
    return App.appName + "/products/" + this.getId();
  }

  public Product(String name, String thumbnail, String slug, float price, int total, float discount, boolean published) {
    this.name = name;
    this.thumbnail = thumbnail;
    this.slug = slug;
    this.price = price;
    this.total = total;
    this.discount = discount;
    this.published = published;
  }

  public Product(Brand brand, String name, String thumbnail, String slug, float price, String imageUrl, int total) {
    this.brand = brand;
    this.name = name;
    this.thumbnail = thumbnail;
    this.slug = slug;
    this.price = price;
    this.imageUrl = imageUrl;
    this.total = total;
  }

  public Product(Brand brand, Long id, String name, String thumbnail, String slug, float price, int total, float discount, boolean published) {
    this.brand = brand;
    this.setId(id);
    this.name = name;
    this.thumbnail = thumbnail;
    this.slug = slug;
    this.price = price;
    this.total = total;
    this.discount = discount;
    this.published = published;
  }
}