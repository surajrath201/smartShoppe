package com.smartShoppe.Repositories;

import com.smartShoppe.Entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<VendorEntity, Long> {

    public Optional<VendorEntity> getByVendorName(String vendorName);
}
