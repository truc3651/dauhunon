package com.dauhunon.Products;

import com.dauhunon.Brands.Brand;
import com.dauhunon.Brands.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  private final ProductRepo productRepo;
  private final BrandRepo brandRepo;

  @Autowired
  public ProductService(ProductRepo productRepo, BrandRepo brandRepo) {
    this.productRepo = productRepo;
    this.brandRepo = brandRepo;
  }

  public List<Product> listAll() {
    return productRepo.findAll();
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

  public Product save(Long brandId, Long id, String name, String thumbnail, String slug, float price, int total, float discount, boolean published) {
    Brand brand = brandRepo.findById(brandId).orElseThrow(() -> new IllegalStateException("Product non exists"));

    Product product = new Product(name, thumbnail, slug, price, total, discount, published);
    product.setBrand(brand);

    if(id != null) {
      productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Product non exists"));
      product.setId(id);
    }
    productRepo.save(product);

    return product;
  }

  public Product delete(Long id) {
    Product product = productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Product non exists"));
    productRepo.deleteById(id);

    return product;
  }

  public Product get(Long id) {
    Product product = productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Product non exists"));
    return product;
  }

  public void assignImage(Long id, String imageUrl) {
    Product product = productRepo.findById(id).orElseThrow(() -> new IllegalStateException("Product non exists"));
    product.setImageUrl(imageUrl);

    productRepo.save(product);
  }
}
