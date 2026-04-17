package org.example.trs2_lab.repository.base;

import org.example.trs2_lab.dto.ProductManufacturerCategory;
import org.example.trs2_lab.entity.Category;
import org.example.trs2_lab.entity.Manufacturer;
import org.example.trs2_lab.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByManufacturerId(Long manufacturerId);

    List<Product> findByPriceBetween(BigDecimal priceStart, BigDecimal priceEnd);


    List<ProductManufacturerCategory> findAllDto();

    @Query("""
    select new org.example.trs2_lab.dto.ProductManufacturerCategory(
        p.id,
        p.name,
        p.price,
        c.id,
        c.name,
        m.id,
        m.name
    )
    from Product p
    join p.category c
    join p.manufacturer m
    where c.id = ?1
""")
    List<ProductManufacturerCategory> findByCategoryIdDto(Long id);

    @Query("""
    select new org.example.trs2_lab.dto.ProductManufacturerCategory(
        p.id,
        p.name,
        p.price,
        c.id,
        c.name,
        m.id,
        m.name
    )
    from Product p
    join p.category c
    join p.manufacturer m
    where m.id = ?1
""")
    List<ProductManufacturerCategory> findByManufacturerIdDto(Long id);

    @Query("""
    select new org.example.trs2_lab.dto.ProductManufacturerCategory(
        p.id,
        p.name,
        p.price,
        c.id,
        c.name,
        m.id,
        m.name
    )
    from Product p
    join p.category c
    join p.manufacturer m
    where p.price between ?1 and ?2
""")
    List<ProductManufacturerCategory> findByPriceBetweenDto(BigDecimal priceStart,
                                                            BigDecimal priceEnd);
}
