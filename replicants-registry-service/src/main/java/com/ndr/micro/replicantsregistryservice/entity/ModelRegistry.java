// package com.ndr.micro.replicantsregistryservice.entity;

// import javax.persistence.Column;
// import javax.persistence.Embeddable;
// import javax.validation.constraints.Size;

// import lombok.Getter;
// import lombok.NoArgsConstructor;

// @Getter
// @Embeddable
// @NoArgsConstructor
// public class ModelRegistry
// {
//     @Size(min = 3)
//     @Column(nullable = false, updatable = false)
//     private String manufacturerName;
    
//     @Column(nullable = false, updatable = false)
//     private Long generation;
    
//     @Column(nullable = false, updatable = false)
//     private Long factoryId;

//     @Column(nullable = false, updatable = false)
//     private Long fisicalId;

//     @Column(nullable = false, updatable = false)
//     private String registryCode;

//     private void buildRegistry()
//     {
//         if (registryCode == null)
//         {
//             registryCode = generation + "-" + manufacturerName.substring(0, 3).toUpperCase() + "." + Long.toHexString(fisicalId).toUpperCase() + " "
//                     + factoryId;
//         }
//     }

//     public ModelRegistry(String manufacturerName, Long generation, Long factoryId, Long fisicalId)
//     {
//         this.manufacturerName = manufacturerName;
//         this.generation = generation;
//         this.factoryId = factoryId;
//         this.fisicalId = fisicalId;
//         buildRegistry();
//     }
// }
