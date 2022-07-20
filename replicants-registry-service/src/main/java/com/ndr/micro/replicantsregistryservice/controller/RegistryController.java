package com.ndr.micro.replicantsregistryservice.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry;
import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry.ReplicantRegistryData;
import com.ndr.micro.replicantsregistryservice.entity.ReplicantRegistry.ReplicantRegistryResponse;
import com.ndr.micro.replicantsregistryservice.service.RegistryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistryController
{
    private final RegistryService registryService;

    @GetMapping
    public ResponseEntity<?> findAllPaged(  @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size, 
                                            @RequestParam(defaultValue = "id") String sort)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<ReplicantRegistryResponse> registries = registryService.getPagedRegistries(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(registries);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createReplicantRegistry(@RequestBody @Valid ReplicantRegistryData registry)
    {
        ReplicantRegistryResponse result = registryService.create(registry);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path = "/change-codename")
    public ResponseEntity<?> changeCodename(@RequestBody ChangeCodenameRequest request)
    {
        ReplicantRegistry registry = registryService.changeCodename(request.id, request.newCodename);

        return ResponseEntity.ok(registry);
    }

    @DeleteMapping(path = "/delete-registry/{id}")
    public ResponseEntity<?> deleteRegistry(@PathVariable UUID id)
    {
        registryService.deleteRegistry(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    class ChangeCodenameRequest
    {
        UUID id;
        String newCodename;
    }
}


