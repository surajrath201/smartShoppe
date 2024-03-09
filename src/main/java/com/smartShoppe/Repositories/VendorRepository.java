package com.smartShoppe.Repositories;

import com.smartShoppe.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
