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

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images_properties")
@Where(clause = "not is_deleted")
@Data
@NoArgsConstructor
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
    private LocalDateTime postedOn;

    //TODO:: Store all value of tika
    @Column(nullable = false, length = 4)
    private String extension;

    @Column(nullable = false)
    private String filename;

    public ImageProperties(UUID registryId)
    {
        this.sequenceNumber = new SequenceNumber();
        this.postedOn = LocalDateTime.now();
        this.isDeleted = false;
        this.registryId = registryId;
    }

    public String normalizeName()
    {
        String sequenceString = getSequenceNumberAsString();

        if (sequenceString.length() < 15)
        {
            StringBuilder sb = new StringBuilder(sequenceString);

            for (int i = 0; i < 15 - (sequenceString.length()); i++)
            {
                sb.insert(0, "0");
            }
            sequenceString = sb.toString();
        }
        return sequenceString;
    }

    public String buildFileName()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(filename);
        sb.append(".");
        sb.append(extension);

        return sb.toString();
    }

    public String getSequenceNumberAsString()
    {
        return this.sequenceNumber.getNumber().toString();
    }

    public void generateFilename()
    {
        this.filename = this.normalizeName();
    }

    @Entity 
    @Getter
    @NoArgsConstructor
    public static class SequenceNumber
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long number;
    }

    @Data
    public static class ImageResponse
    {
        private UUID id;
        private UUID registryId;
        private LocalDateTime postedOn;
        private byte[] image;

        public ImageResponse(ImageProperties imgProperties, byte[] image)
        {
            this.id = imgProperties.getId();
            this.registryId = imgProperties.getRegistryId();
            this.postedOn = imgProperties.postedOn;
            this.image = image;
        }
    }
}