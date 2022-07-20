package com.ndr.micro.replicantsregistryservice.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry;

@Repository
public interface RegistryRepository extends PagingAndSortingRepository<ReplicantRegistry, UUID>
{
    
    
}
