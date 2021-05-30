package com.dauhunon.Brands;

import javax.persistence.*;

import com.dauhunon.Products.Product;
import com.dauhunon.common.BaseEntity;
import com.dauhunon.utils.App;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "brands", uniqueConstraints=@UniqueConstraint(columnNames="name"))
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand extends BaseEntity implements Serializable {
  @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JsonIgnore
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
  public String getPublicId() {
    return App.appName + "/brands/" + this.getId();
  }

  public Brand(String name, int totalProducts, String thumbnail, boolean published) {
    this.name = name;
    this.totalProducts = totalProducts;
    this.thumbnail = thumbnail;
    this.published = published;
  }

  public Brand(Set<Product> products, String name, int totalProducts, String thumbnail, String imageUrl) {
    this.products = products;
    this.name = name;
    this.totalProducts = totalProducts;
    this.thumbnail = thumbnail;
    this.imageUrl = imageUrl;
  }
}