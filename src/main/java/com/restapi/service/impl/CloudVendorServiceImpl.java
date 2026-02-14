package com.restapi.service.impl;

import com.restapi.exception.CloudVendorNotFoundException;
import com.restapi.model.CloudVendor;
import com.restapi.repository.CloudVendorRepository;
import com.restapi.service.CloudVendorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudVendorServiceImpl implements CloudVendorService {

    CloudVendorRepository cloudVendorRepository;

    public CloudVendorServiceImpl(CloudVendorRepository cloudVendorRepository) {
        this.cloudVendorRepository = cloudVendorRepository;
    }

    @Override
    public CloudVendor createCloudVendor(CloudVendor cloudVendor) {
        // More Business Logic
        return cloudVendorRepository.save(cloudVendor);
    }

    @Override
    public CloudVendor updateCloudVendor(CloudVendor cloudVendor) {
        // More Business Logic
        return cloudVendorRepository.save(cloudVendor);
    }

    @Override
    public void deleteCloudVendor(String cloudVendorId) {
        // More Business Logic
        cloudVendorRepository.deleteById(cloudVendorId);
    }

    @Override
    public CloudVendor getCloudVendor(String cloudVendorId) {
        // More Business Logic
        if(cloudVendorRepository.findById(cloudVendorId).isEmpty())
            throw new CloudVendorNotFoundException("Requested Cloud Vendor does not exist");
        return cloudVendorRepository.findById(cloudVendorId).get();
    }

    @Override
    public List<CloudVendor> getAllCloudVendors() {
        // More Business Logic
        return cloudVendorRepository.findAll();
    }

    @Override
    public List<CloudVendor> getByVendorName(String vendorName)
    {
        return cloudVendorRepository.findByVendorName(vendorName);
    }

    @Override
    public Page<CloudVendor> getAllCloudVendors(int page, int size) {
        return cloudVendorRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<CloudVendor> getByVendorName(String vendorName, int page, int size) {
        return cloudVendorRepository.findByVendorName(vendorName, PageRequest.of(page, size));
    }
}
