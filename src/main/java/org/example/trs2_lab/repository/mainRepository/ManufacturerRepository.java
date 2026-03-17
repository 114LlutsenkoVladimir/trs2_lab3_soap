package org.example.trs2_lab.repository.mainRepository;

import org.example.trs2_lab.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}