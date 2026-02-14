package com.restapi.repository;

import com.restapi.model.CloudVendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CloudVendorRepository extends JpaRepository<CloudVendor, String> {
    List<CloudVendor> findByVendorName(String vendorName);
    Page<CloudVendor> findByVendorName(String vendorName, Pageable pageable);
}
