package com.dauhunon.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
  private final ProductRepo productRepo;

  @Autowired
  public ProductService(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  public Page<Product> listAll(
    int currentPage,
    String sortField,
    String sortDir,
    int recordNumber,
    String filter,
    String keyword){

    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    Pageable page = PageRequest.of(currentPage-1, recordNumber, sort);
    if(keyword != null)
      keyword = "";
    if(filter.equals("all")) return productRepo.listAll(keyword, page);

    boolean published = Boolean.parseBoolean(filter);
    return productRepo.listAllWherePublished(keyword, published, page);
  }

  public Product create(String name, String thumbnail, String slug, float price, int total, float discount, boolean published) {
    Product product = new Product(name, thumbnail, slug, price, total, discount, published);
    productRepo.save(product);

    return product;
  }

  public Product update(Long id, String name, String thumbnail, String slug, float price, int total, float discount, boolean published) {
    Product product = productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));

    product.setName(name);
    product.setThumbnail(thumbnail);
    product.setSlug(slug);
    product.setPrice(price);
    product.setTotal(total);
    product.setDiscount(discount);
    product.setPublished(published);

    productRepo.save(product);

    return product;
  }

  public Product delete(Long id) {
    Product product = productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));

    productRepo.deleteById(id);
    return product;
  }

  public void assignImage(Long id, String imageUrl) {
    Product product = productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));
    product.setImageUrl(imageUrl);

    productRepo.save(product);
  }
}
