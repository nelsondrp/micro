package com.ndr.micro.replicantsregistryservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry;
import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry.ReplicantRegistryData;
import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry.ReplicantRegistryResponse;
import com.ndr.micro.replicantsregistryservice.repository.RegistryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistryService
{
    private final RegistryRepository regRepository;
    
    public Page<ReplicantRegistryResponse> getPagedRegistries(Pageable pageable)
    {
        Page<ReplicantRegistryResponse> registries = 
            regRepository.findAll(pageable)
                .map(registry -> new ReplicantRegistryResponse(registry));

        return registries;
    }

    public ReplicantRegistryResponse create(ReplicantRegistryData registry)
    {
        return new  ReplicantRegistryResponse(regRepository.save(registry.toEntity()));
    }

    public ReplicantRegistry changeCodename(UUID id, String newCodename)
    {
        ReplicantRegistry registry = regRepository.findById(id).orElseThrow();
        registry.setCodename(newCodename);

        ReplicantRegistry updatedRegistry = regRepository.save(registry);

        return updatedRegistry;
    }

    public void deleteRegistry(UUID id)
    {
        ReplicantRegistry registry = regRepository.findById(id).orElseThrow();
        registry.setIsDeleted(true);

        regRepository.save(registry);
    }
}
