package com.dauhunon.Carts;

import com.dauhunon.Products.Product;
import com.dauhunon.Users.User;
import com.dauhunon.common.BaseEntity;
import com.dauhunon.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart extends BaseEntity implements Serializable {
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name="cart_product",
    joinColumns = @JoinColumn(name = "cart_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private Set<Product> products = new HashSet<>();

  @ManyToOne(optional = false)
  @JoinColumn(name="user_id", referencedColumnName = "id")
  @JsonIgnore
  private User user;

  private float total;

  private float shippingPrice;

  @Column(columnDefinition = "int default " + Const.DEFAULT_CART_STATUS)
  private int status;

  public Cart(float total, float shippingPrice) {
    this.total = total;
    this.shippingPrice = shippingPrice;
  }

  public void addProduct(Product product) {
    this.products.add(product);
  }

  public Cart(User user, float total, float shippingPrice, int status) {
    this.user = user;
    this.total = total;
    this.shippingPrice = shippingPrice;
    this.status = status;
  }

  public Cart(User user, float total, float shippingPrice) {
    this.user = user;
    this.total = total;
    this.shippingPrice = shippingPrice;
  }
}
