package com.ndr.micro.imageservice.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ndr.micro.imageservice.entity.ImageProperties;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<ImageProperties, UUID>{
    
}
