package org.example.trs2_lab.repository.mainRepository;

import org.example.trs2_lab.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}