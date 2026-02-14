package com.restapi.controller;

import com.restapi.model.CloudVendor;
import com.restapi.response.ResponseHandler;
import com.restapi.service.CloudVendorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cloudvendor")
public class CloudVendorController
{
    CloudVendorService cloudVendorService;

    public CloudVendorController(CloudVendorService cloudVendorService) {
        this.cloudVendorService = cloudVendorService;
    }

    // Read Specific Cloud Vendor Details from DB
    @GetMapping("/{vendorId}")
    @ApiOperation(value ="Cloud vendor id", notes="Provide cloud vendor details",
                    response = ResponseEntity.class)
    public ResponseEntity<Object> getCloudVendorDetails(@PathVariable("vendorId") String vendorId)
    {
       return ResponseHandler.responseBuilder("Requested Vendor Details are given here",
                HttpStatus.OK, cloudVendorService.getCloudVendor(vendorId));
    }

    // Read All Cloud Vendor Details from DB
    // Read All Cloud Vendor Details from DB with Pagination and Filtering
    @GetMapping("/")
    public ResponseEntity<Object> getAllCloudVendorDetails(
            @RequestParam(required = false) String vendorName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (vendorName != null && !vendorName.isEmpty()) {
            return ResponseHandler.responseBuilder("Requested Vendor Details are given here",
                    HttpStatus.OK, cloudVendorService.getByVendorName(vendorName, page, size));
        }
        return ResponseHandler.responseBuilder("Requested Vendor Details are given here",
                HttpStatus.OK, cloudVendorService.getAllCloudVendors(page, size));
    }

    @PostMapping("/")
    public ResponseEntity<Object> createCloudVendorDetails(@Valid @RequestBody CloudVendor cloudVendor)
    {
        CloudVendor createdVendor = cloudVendorService.createCloudVendor(cloudVendor);
        return ResponseHandler.responseBuilder("Cloud Vendor Created Successfully", HttpStatus.CREATED, createdVendor);
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateCloudVendorDetails(@RequestBody CloudVendor cloudVendor)
    {
        CloudVendor updatedVendor = cloudVendorService.updateCloudVendor(cloudVendor);
        return ResponseHandler.responseBuilder("Cloud Vendor Updated Successfully", HttpStatus.OK, updatedVendor);
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Object> deleteCloudVendorDetails(@PathVariable("vendorId") String vendorId)
    {
        cloudVendorService.deleteCloudVendor(vendorId);
        return ResponseHandler.responseBuilder("Cloud Vendor Deleted Successfully", HttpStatus.OK, null);
    }
}
