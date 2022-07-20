package com.ndr.micro.imageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ndr.micro.imageservice.entity.ImageProperties.SequenceNumber;

@Repository
public interface SequenceRepository extends JpaRepository<SequenceNumber, Long>{
    
}
