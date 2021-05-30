package com.dauhunon.Brands;

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
@RequestMapping("/brands")
public class BrandCtrl {
  private final BrandService brandService;

  @Autowired
  public BrandCtrl(BrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping
  public String viewBrands(
    @RequestParam(name = "page", defaultValue = "1") int page,
    @RequestParam(name = "sortField", defaultValue = "createdAt") String sortField,
    @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir,
    @RequestParam(name = "recordNumber", defaultValue = "5") int recordNumber,
    @RequestParam(name = "filter", defaultValue = "all") String filter,
    @RequestParam(name = "keyword", defaultValue = "") String keyword,
    Model model
  ) {
    Page<Brand> pageContent = brandService.listAll(page, sortField, sortDir, recordNumber, filter, keyword);
    List<Brand> content = pageContent.getContent();

    int totalPages = pageContent.getTotalPages();
    int totalItems = pageContent.getNumberOfElements();

    model.addAttribute("listBrands", content);
    model.addAttribute("page", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("totalItems", totalItems);
    model.addAttribute("recordNumber", recordNumber);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("filter", filter);
    model.addAttribute("keyword", keyword);

    return "manage/brands";
  }

  @PostMapping("/createOrUpdate")
  public RedirectView createOrUpdate(
    @RequestParam(name = "image", required = false) MultipartFile multipartFile,
    @RequestParam(name = "id", required = false) Long id,
    @Param("name") String name,
    @Param("totalProducts") int totalProducts,
    @Param("thumbnail") String thumbnail,
    @Param("published") boolean published
  ) throws IOException {

    Brand brand = brandService.save(id, name, totalProducts, thumbnail, published);

    if(!multipartFile.isEmpty()) {
      FileHandler.deleteFile(brand.getPublicId());

      Map uploadResult = FileHandler.uploadFile(multipartFile, "brands", brand.getId()+"");
      String secureUrl = (String) uploadResult.get("secure_url");

      brandService.assignImage(brand.getId(), secureUrl);
    }

    return new RedirectView("/brands/", true);
  }

  @PostMapping("/delete")
  public RedirectView delete(
    @Param("id") Long id
  ) throws IOException {

    Brand brand = brandService.delete(id);
    FileHandler.deleteFile(brand.getPublicId());

    return new RedirectView("/brands/", true);
  }

  @ModelAttribute("filters")
  public Map<String, String> getFilters() {
    Map<String, String> filters = new HashMap<>();

    filters.put("all","all");
    filters.put("true", "published");
    filters.put("false", "un-published");

    return filters;
  }
}
