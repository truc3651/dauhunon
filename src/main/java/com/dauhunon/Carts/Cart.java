package com.dauhunon.Carts;

import com.dauhunon.CartItems.CartItems;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
//  @ManyToMany(fetch = FetchType.EAGER)
//  @JoinTable(
//    name="cart_product",
//    joinColumns = @JoinColumn(name = "cart_id"),
//    inverseJoinColumns = @JoinColumn(name = "product_id")
//  )
//  private Set<Product> products = new HashSet<>();
//
//  @ManyToMany(fetch = FetchType.EAGER)
//  @JoinTable(
//    name="cart_product",
//    joinColumns = @JoinColumn(name = "cart_id"),
//    inverseJoinColumns = @JoinColumn(name = "total")
//  )
//
//  @ManyToOne(optional = false)
//  @JoinColumn(name="total")
//  @JsonIgnore
//  private Map<Long, Integer> totalProduct = new HashMap<>();
//
//  @ManyToOne(optional = false)
//  @JoinColumn(name="user_id", referencedColumnName = "id")
//  @JsonIgnore
//  private User user;
//
//  private int totalProducts;
//
//  private float totalPrice;
//
//  private float shippingPrice;
//
//  @Column(columnDefinition = "varchar(20) default '" + Const.PENDING + "'")
//  private String status;
//
//  public void addProduct(Product product) {
//    this.products.add(product);
////    int oldTotal = this.totalProduct.get(product.getId());
////    int total = Integer.valueOf(oldTotal) != null && oldTotal > 1 ? oldTotal+=1 : 1;
////    this.totalProduct.put(product.getId(), total);
//  }
//
//  public Cart(User user, int totalProducts, float totalPrice, float shippingPrice, String status) {
//    this.user = user;
//    this.totalProducts = totalProducts;
//    this.totalPrice = totalPrice;
//    this.shippingPrice = shippingPrice;
//    this.status = status;
//  }
//
//  public Cart(User user, int totalProducts, float totalPrice, float shippingPrice) {
//    this.user = user;
//    this.totalProducts = totalProducts;
//    this.totalPrice = totalPrice;
//    this.shippingPrice = shippingPrice;
//  }

  @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<CartItems> cartItems;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private int totalProducts;

  @Column(nullable = false)
  private float totalPrice;

  @Column(columnDefinition = "varchar(20) default '" + Const.PENDING + "'")
  private String status;

  public Cart(User user, int totalProducts, float totalPrice) {
    this.user = user;
    this.totalProducts = totalProducts;
    this.totalPrice = totalPrice;
  }

  //  public void addProduct(Product product) {
//    this.products.add(product);
////    int oldTotal = this.totalProduct.get(product.getId());
////    int total = Integer.valueOf(oldTotal) != null && oldTotal > 1 ? oldTotal+=1 : 1;
////    this.totalProduct.put(product.getId(), total);
//  }
//
//  public Cart(User user, int totalProducts, float totalPrice, float shippingPrice, String status) {
//    this.user = user;
//    this.totalProducts = totalProducts;
//    this.totalPrice = totalPrice;
//    this.shippingPrice = shippingPrice;
//    this.status = status;
//  }
//
//  public Cart(User user, int totalProducts, float totalPrice, float shippingPrice) {
//    this.user = user;
//    this.totalProducts = totalProducts;
//    this.totalPrice = totalPrice;
//    this.shippingPrice = shippingPrice;
//  }
}
