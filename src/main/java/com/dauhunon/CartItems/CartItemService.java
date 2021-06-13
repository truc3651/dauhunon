package com.dauhunon.CartItems;

import com.dauhunon.Carts.Cart;
import com.dauhunon.Carts.CartRepo;
import com.dauhunon.Carts.CartService;
import com.dauhunon.Products.Product;
import com.dauhunon.Products.ProductService;
import com.dauhunon.Users.User;
import com.dauhunon.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartItemService {
  private final CartItemsRepo cartItemsRepo;
  private final CartRepo cartRepo;
  private final CartService cartService;
  private final ProductService productService;

  @Autowired
  public CartItemService(
    CartItemsRepo cartItemsRepo,
    CartRepo cartRepo,
    CartService cartService,
    ProductService productService
  ) {
    this.cartItemsRepo = cartItemsRepo;
    this.cartRepo = cartRepo;
    this.cartService = cartService;
    this.productService = productService;
  }

  public CartItems getByCartAndProduct(long userId, long productId) {
    List<CartItems> cartItems = cartItemsRepo.getPendingCartItem(userId, productId);
    if(cartItems == null || cartItems.size() <= 0)
      throw new IllegalStateException("Cart item non exists");
    return cartItems.get(0);
  }

  public CartItems findById(long id) {
    CartItems cartItem = cartItemsRepo.findById(id).orElseThrow(() -> new IllegalStateException("Cart item non exists"));
    return cartItem;
  }

  public void addCartItem(User user, long productId, int total) {
    Product product = productService.get(productId);
    float totalPrice = total * product.getPrice();

    Cart cart = null;
    try {
//    get logged in user's pending cart
      cart = cartService.getPendingCart(user.getId());
      cart.setTotalProducts(cart.getTotalProducts() + 1);
      cart.setTotalPrice(cart.getTotalPrice() + totalPrice);
    } catch (Exception e) {
//    create new pending cart for user
      cart = new Cart(user, 1, totalPrice);
    }
    cartRepo.save(cart);

    CartItems cartItem = null;
    try {
//      get logged in user's cart item based on inputted product
      cartItem = getByCartAndProduct(user.getId(), product.getId());
      cartItem.setTotalProducts(cartItem.getTotalProducts() + 1);
    } catch (Exception e) {
//      create new cart item based on inputted product
      cartItem = new CartItems(cart, product, 1, totalPrice);
    }
    cartItemsRepo.save(cartItem);
  }

  public void removeCartItem(long id, boolean isAll) {
    //      remove cart item
    CartItems cartItem = findById(id);
    int oldCartItemTotalProducts = cartItem.getTotalProducts();
    int updatedCartItemTotalProducts = oldCartItemTotalProducts - 1;
    if(updatedCartItemTotalProducts <= 0 || isAll)
      cartItemsRepo.delete(cartItem);
    else {
      cartItem.setTotalProducts(updatedCartItemTotalProducts);
      cartItemsRepo.save(cartItem);
    }
//      if cart empty, then remove
    Cart cart = cartService.getPendingCart(cartItem.getCart().getUser().getId());
    int oldCartTotalProducts = cart.getTotalProducts();
    int updatedCartTotalProducts = isAll ? oldCartTotalProducts - oldCartItemTotalProducts : oldCartTotalProducts - 1;
    if(updatedCartTotalProducts <= 0)
      cartRepo.delete(cart);
    else {
      cart.setTotalProducts(updatedCartTotalProducts);
      cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice());
      cartRepo.save(cart);
    }
  }
}
