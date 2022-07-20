package com.ndr.micro.replicantsregistryservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "replicant_registry")
@Where(clause = "not is_deleted")
@Data
@NoArgsConstructor
public class ReplicantRegistry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime manufactureDate;

    @Embedded
    @Setter(AccessLevel.NONE)
    private ModelRegistry model;

    @Column(nullable = true)
    private String codename;

    @Column(nullable = false)
    private Boolean isDeleted;

    @JsonIgnore
    public String getRegistry()
    {
        return this.model.getRegistryCode();
    }

    public ReplicantRegistry(String manufacturerName, Long generation, Long factoryId, Long fisicalId,
                            String codename)
    {
        this.manufactureDate = LocalDateTime.now();
        this.codename = codename;
        this.model = new ModelRegistry(manufacturerName, generation, factoryId, fisicalId);
        this.isDeleted = false;
    }

    @Getter
    @Embeddable
    @NoArgsConstructor
    public static class ModelRegistry
    {
        @Size(min = 3)
        @Column(nullable = false, updatable = false)
        private String manufacturerName;
        
        @Column(nullable = false, updatable = false)
        private Long generation;
        
        @Column(nullable = false, updatable = false)
        private Long factoryId;

        @Column(nullable = false, updatable = false)
        private Long fisicalId;

        @Column(nullable = false, updatable = false)
        private String registryCode;

        private void buildRegistry()
        {
            if (registryCode == null)
            {
                registryCode = generation + "-" + manufacturerName.substring(0, 3).toUpperCase() + "." + Long.toHexString(fisicalId).toUpperCase() + " "
                        + factoryId;
            }
        }

        public ModelRegistry(String manufacturerName, Long generation, Long factoryId, Long fisicalId)
        {
            this.manufacturerName = manufacturerName;
            this.generation = generation;
            this.factoryId = factoryId;
            this.fisicalId = fisicalId;
            buildRegistry();
        }
    }


    @Getter
    public static class ReplicantRegistryResponse
    {
        private UUID id;
        private LocalDateTime manufactureDate;
        private String manufacturerName;
        private Long generation;
        private Long factoryId;
        private Long fisicalId;
        private String registryCode;
        private String codename;

        public ReplicantRegistryResponse(ReplicantRegistry entity)
        {
            this.id = entity.id;
            this.manufactureDate = entity.manufactureDate;
            this.manufacturerName = entity.model.getManufacturerName();
            this.generation = entity.model.getGeneration();
            this.factoryId = entity.model.getFactoryId();
            this.fisicalId = entity.model.getFisicalId();
            this.registryCode = entity.model.getRegistryCode();
            this.codename = entity.codename;
        }
    }

    @Data
    public static class ReplicantRegistryData
    {
        @Size(min = 3, message = "Manufacturer name must contain at least 3 characters")
        private String manufacturerName;

        @Min(value = 1, message = "Generation must be higher than zero")
        private Long generation;

        @Min(value = 1, message = "Factory ID must be higher than zero")
        private Long factoryId;

        @Min(value = 1, message = "Fisical ID must be higher than zero")
        private Long fisicalId;

        private String codename;

        public ReplicantRegistry toEntity()
        {
            return new ReplicantRegistry(manufacturerName, generation, factoryId, fisicalId, codename);
        }
    }
}
