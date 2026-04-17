package org.example.trs2_lab.service;

import org.example.trs2_lab.dbType.DbSelectionContext;
import org.example.trs2_lab.dbType.DbType;
import org.example.trs2_lab.dto.ProductManufacturerCategory;
import org.example.trs2_lab.entity.Category;
import org.example.trs2_lab.entity.Manufacturer;
import org.example.trs2_lab.entity.Product;
import org.example.trs2_lab.repoProvider.ProductRepoProvider;
import org.example.trs2_lab.repository.base.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService extends AbstractCrudService<Product, Long,
        ProductRepository, ProductRepoProvider>{

    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public ProductService(ProductRepoProvider repoProvider,
                             DbSelectionContext dbContext,
                          CategoryService categoryService,
                          ManufacturerService manufacturerService) {
        super(repoProvider, dbContext);
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return currentRepo().findByCategoryId(categoryId);
    }

    public List<Product> findByManufacturerId(Long manufacturerId) {
        return currentRepo().findByManufacturerId(manufacturerId);
    }

    public List<Product> findByPriceBetween(BigDecimal start, BigDecimal end) {
        return currentRepo().findByPriceBetween(start, end);
    }

    public List<ProductManufacturerCategory> findAllDto() {
        return currentRepo().findAllDto();
    }

    public List<ProductManufacturerCategory> findAllDto(DbType dbType) {
        return repoProvider.getRepo(dbType).findAllDto();
    }

    public List<ProductManufacturerCategory> findByCategoryIdDto(Long id) {
        return currentRepo().findByCategoryIdDto(id);
    }

    public List<ProductManufacturerCategory> findByManufacturerIdDto(Long id) {
        return currentRepo().findByManufacturerIdDto(id);
    }

    public List<ProductManufacturerCategory> findByPriceBetweenDto(BigDecimal priceStart,
                                                                   BigDecimal priceEnd) {
        return currentRepo().findByPriceBetweenDto(priceStart, priceEnd);
    }

    public Product updateByDto(ProductManufacturerCategory productDto) {
        System.out.println("==========================" + productDto.getId() + "=================================");
        Product product = findById(productDto.getId()).orElseThrow(
                () -> new RuntimeException("Product did not found id: " + productDto.getId())
        );
        product.setName(productDto.getName());
        Category category = categoryService.findById(productDto.getCategoryId()).orElseThrow(
                () -> new RuntimeException("Product did not found id: " + productDto.getCategoryId())
        );
        product.setCategory(category);
        Manufacturer manufacturer = manufacturerService.findById(productDto.getManufacturerId()).orElseThrow(
                () -> new RuntimeException("Manufacturer did not found id: " + productDto.getManufacturerId())
        );
        product.setManufacturer(manufacturer);
        return update(product);
    }
}
