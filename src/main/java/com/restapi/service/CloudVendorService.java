package com.restapi.service;

import com.restapi.model.CloudVendor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CloudVendorService {
    public CloudVendor createCloudVendor(CloudVendor cloudVendor);
    public CloudVendor updateCloudVendor(CloudVendor cloudVendor);
    public void deleteCloudVendor(String cloudVendorId);
    public CloudVendor getCloudVendor(String cloudVendorId);
    public List<CloudVendor> getAllCloudVendors();
    public Page<CloudVendor> getAllCloudVendors(int page, int size);
    public List<CloudVendor> getByVendorName(String vendorName);
    public Page<CloudVendor> getByVendorName(String vendorName, int page, int size);
}
