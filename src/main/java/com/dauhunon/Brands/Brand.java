package com.dauhunon.Brands;

import javax.persistence.*;

import com.dauhunon.Products.Product;
import com.dauhunon.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "brands", uniqueConstraints=@UniqueConstraint(columnNames="name"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand extends BaseEntity implements Serializable {
  @OneToMany(mappedBy = "brand")
  private Set<Product> products;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private int totalProducts;

  @Column(nullable = false)
  private String thumbnail;

  private String imageUrl;

  @Column(columnDefinition = "boolean default true")
  private boolean published;

  @Transient
  public String getImagePath() {
    if(this.getId() == null) return null;
    return "/photos/brands/" + this.getImageUrl();
  }

  public Brand(String name, int totalProducts, String thumbnail, boolean published) {
    this.name = name;
    this.totalProducts = totalProducts;
    this.thumbnail = thumbnail;
    this.published = published;
  }
}
