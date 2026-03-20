package org.example.trs2_lab.controller;

import org.example.trs2_lab.dbType.DbSelectionContext;
import org.example.trs2_lab.entity.Category;
import org.example.trs2_lab.entity.Manufacturer;
import org.example.trs2_lab.entity.Product;
import org.example.trs2_lab.service.CategoryService;
import org.example.trs2_lab.service.ManufacturerService;
import org.example.trs2_lab.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController extends AbstractDbContextController<Product>{

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             ManufacturerService manufacturerService,
                             CategoryService categoryService,
                             DbSelectionContext dbContext) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
        super(dbContext, "mysqlProducts",
                "postgresProducts");
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("manufacturers")
    public List<Manufacturer> getManufacturers() {
        return manufacturerService.findAll();
    }

    @GetMapping("/")
    public String init(Model model) {
        model.addAttribute("mysqlProducts",
                productService.findAll());
        model.addAttribute("postgresProducts",
                productService.findAll());
        model.addAttribute("selectedManufacturerId", 1L);
        model.addAttribute("selectedCategoryId", 1L);
        return "products";
    }

    @GetMapping("/by-category")
    public String findByCategory(@RequestParam Long categoryId,
                                 Model model) {
        updateRelevantTable(productService.findByCategoryId(categoryId), model);
        return "products";
    }

    @GetMapping("/by-manufacturer")
    public String findByManufacturer(@RequestParam Long manufacturerId,
                                     Model model) {
        updateRelevantTable(productService.findByManufacturerId(manufacturerId), model);
        return "products";
    }

    @GetMapping("/by-price")
    public String findByPrice(@RequestParam BigDecimal priceStart,
                              @RequestParam BigDecimal priceEnd,
                              Model model) {
        updateRelevantTable(productService.findByPriceBetween(priceStart, priceEnd), model);
        return "products";
    }

}
