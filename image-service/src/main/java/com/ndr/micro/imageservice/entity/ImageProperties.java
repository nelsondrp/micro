package com.ndr.micro.imageservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "images")
@Data
public class ImageProperties
{
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID registryId;

    @OneToOne
    private SequenceNumber sequenceNumber;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private LocalDateTime date;

    public ImageProperties(UUID registryId)
    {
        this.sequenceNumber = this.new SequenceNumber();
        this.date = LocalDateTime.now();
        this.isDeleted = false;
        this.registryId = registryId;
    }

    @Entity @Getter
    public class SequenceNumber
    {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long number;
    }
}