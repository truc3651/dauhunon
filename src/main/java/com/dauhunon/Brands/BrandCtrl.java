package com.dauhunon.Brands;

import com.dauhunon.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

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
    Model model
  ) {
    List<Brand> listBrands = brandService.list();

    model.addAttribute("listBrands", listBrands);
    return "manage/brands";
  }

  @PostMapping("/createOrUpdate")
  public RedirectView createOrUpdate(
    @RequestParam(name = "image", required = false) MultipartFile multipartFile,
    @RequestParam(name = "imageUrl", required = false) String imageUrl,
    @RequestParam(name = "id", required = false) Long id,
    @Param("name") String name,
    @Param("totalProducts") int totalProducts,
    @Param("thumbnail") String thumbnail,
    @Param("published") boolean published
  ) throws IOException {
    Brand brand = null;

    if(id != null) {
      brand = brandService.update(id, name, totalProducts, thumbnail, published);
    } else {
      brand = brandService.create(name, totalProducts, thumbnail, published);
    }

    if(!multipartFile.isEmpty()) {
      String fileName = FileHandler.getPhotosName(multipartFile, String.valueOf(brand.getId()));
      String uploadDir = "photos/brands/";

      FileHandler.saveFile(uploadDir, fileName, multipartFile);
      brandService.assignImage(brand.getId(), fileName);
    }

    return new RedirectView("/brands/", true);
  }

//  @GetMapping
//  public String viewBrands(
//    @Param("page")int page,
//    @Param("sortField")String sortField,
//    @Param("sortDir")String sortDir,
//    @Param("recordNumber")int recordNumber,
//    @Param("keyword")String keyword,
//    Model model
//  ) {
//    Page<Brand> pageContent = brandService.listAll(page, sortField, sortDir, recordNumber, keyword);
//    List<Brand> content = pageContent.getContent();
//
//    int totalPages = pageContent.getTotalPages();
//    int totalItems = pageContent.getNumberOfElements();
//
//    model.addAttribute("listBrands", content);
//    model.addAttribute("page", page);
//    model.addAttribute("totalPages", totalPages);
//    model.addAttribute("totalItems", totalItems);
//    model.addAttribute("sortField", sortField);
//    model.addAttribute("sortDir", sortDir);
//    model.addAttribute("recordNumber", recordNumber);
//    model.addAttribute("keyword", keyword);
//
//    return "manage/brands";
//  }
}
