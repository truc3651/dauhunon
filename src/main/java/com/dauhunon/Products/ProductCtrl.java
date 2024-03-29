package com.dauhunon.Products;

import com.dauhunon.Brands.Brand;
import com.dauhunon.Brands.BrandService;
import com.dauhunon.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductCtrl {
  private final ProductService productService;
  private final BrandService brandService;

  @Autowired
  public ProductCtrl(ProductService productService, BrandService brandService) {
    this.productService = productService;
    this.brandService = brandService;
  }

  @GetMapping
  public String viewProducts(
    @RequestParam(name = "page", defaultValue = "1") int page,
    @RequestParam(name = "sortField", defaultValue = "createdAt") String sortField,
    @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir,
    @RequestParam(name = "recordNumber", defaultValue = "5") int recordNumber,
    @RequestParam(name = "filter", defaultValue = "all") String filter,
    @RequestParam(name = "keyword", defaultValue = "") String keyword,
    Model model
  ) {
    Page<Product> pageContent = productService.listAll(page, sortField, sortDir, recordNumber, filter, keyword);
    List<Product> content = pageContent.getContent();

    int totalPages = pageContent.getTotalPages();
    int totalItems = pageContent.getNumberOfElements();

    model.addAttribute("listProducts", content);
    model.addAttribute("page", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("totalItems", totalItems);
    model.addAttribute("recordNumber", recordNumber);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("filter", filter);
    model.addAttribute("keyword", keyword);

    return "manage/products";
  }

  @PostMapping("/createOrUpdate")
  public RedirectView createOrUpdate(
    @RequestParam(name = "image", required = false) MultipartFile multipartFile,
    @Param("brandId") Long brandId,
    @RequestParam(name = "id", required = false) Long id,
    @Param("name") String name,
    @Param("thumbnail") String thumbnail,
    @Param("slug") String slug,
    @Param("price") float price,
    @Param("total") int total,
    @Param("discount") float discount,
    @Param("published") boolean published
  ) throws IOException {

    Product product = productService.save(brandId, id, name, thumbnail, slug, price, total, discount, published);

    if(!multipartFile.isEmpty()) {
      FileHandler.deleteFile(product.getPublicId());

      Map uploadResult = FileHandler.uploadFile(multipartFile, "products", product.getId()+"");
      String secureUrl = (String) uploadResult.get("secure_url");

      productService.assignImage(product.getId(), secureUrl);
    }
    return new RedirectView("/products/", true);
  }

  @PostMapping("/delete")
  public RedirectView delete(
    @Param("id") Long id
  ) throws IOException {

    Product product = productService.delete(id);
    FileHandler.deleteFile(product.getPublicId());

    return new RedirectView("/products/", true);
  }

  @GetMapping("/{id}")
  public String viewDetailProduct(
    @PathVariable ("id") Long id,
    Model model
  ) {
    Product product = productService.get(id);
    model.addAttribute("product", product);
    return "products/detail";
  }

  @ModelAttribute("filters")
  public Map<String, String> getFilters() {
    Map<String, String> filters = new HashMap<>();

    filters.put("all","all");
    filters.put("true", "published");
    filters.put("false", "un-published");

    return filters;
  }

  @ModelAttribute("brands")
  public List<Brand> getBrands() {
    List<Brand> brands = brandService.listAll();
    return brands;
  }
}
