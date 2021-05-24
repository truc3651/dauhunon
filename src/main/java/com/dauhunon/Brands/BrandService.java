package com.dauhunon.Brands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
  private final BrandRepo brandRepo;

  @Autowired
  public BrandService(BrandRepo brandRepo) {
    this.brandRepo = brandRepo;
  }

  public List<Brand> listAll() {
    return brandRepo.findAll();
  }

  public Page<Brand> listAll(
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
    if(filter.equals("all")) return brandRepo.listAll(keyword, page);

    boolean published = Boolean.parseBoolean(filter);
    return brandRepo.listAllWherePublished(keyword, published, page);
  }

  public Brand create(String name, int totalProducts, String thumbnail, boolean published) {
    Brand brandByName = brandRepo.findByName(name.toLowerCase());
    if(brandByName != null) throw new IllegalStateException("Name taken");

    Brand brand = new Brand(name, totalProducts, thumbnail, published);
    brandRepo.save(brand);

    return brand;
  }

  public Brand update(Long id, String name, int totalProducts, String thumbnail, boolean published) {
    Brand brand = brandRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));

    brand.setName(name);
    brand.setTotalProducts(totalProducts);
    brand.setThumbnail(thumbnail);
    brand.setPublished(published);

    brandRepo.save(brand);

    return brand;
  }

  public Brand delete(Long id) {
    Brand brand = brandRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));

    brandRepo.deleteById(id);
    return brand;
  }

  public void assignImage(Long id, String imageUrl) {
    Brand brand = brandRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));
    brand.setImageUrl(imageUrl);

    brandRepo.save(brand);
  }
}
