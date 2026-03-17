package org.example.trs2_lab.repository.mainRepository;

import org.example.trs2_lab.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}