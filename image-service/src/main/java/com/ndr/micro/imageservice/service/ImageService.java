package com.ndr.micro.imageservice.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ndr.micro.imageservice.entity.ImageProperties;
import com.ndr.micro.imageservice.entity.ImageProperties.ImageResponse;
import com.ndr.micro.imageservice.entity.ImageProperties.SequenceNumber;
import com.ndr.micro.imageservice.repository.ImageRepository;
import com.ndr.micro.imageservice.repository.SequenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService
{
    private final ImageRepository imagePropertiesRepository;
    private final SequenceRepository seqRepository;
    private final StorageService storageService;

    @Transactional(rollbackOn = Exception.class)
    public ImageProperties save(MultipartFile image, UUID registryId) throws IllegalArgumentException, IOException
    {
        ImageProperties imgProperties = new ImageProperties(registryId);
        SequenceNumber sequenceNumber = seqRepository.save(imgProperties.getSequenceNumber());

        imgProperties.setSequenceNumber(sequenceNumber);
        imgProperties.generateFilename();
        imgProperties.setExtension(storageService.getRealExtensionFromFile(image.getInputStream()));
        
        imgProperties = imagePropertiesRepository.save(imgProperties);

        storageService.storeImage(imgProperties, image);
        
        return imgProperties;
    }

    public Page<ImageResponse> loadByRegistryId(UUID registryId, Pageable pageable)
    {
        Page<ImageProperties> imgProperties = imagePropertiesRepository.findByRegistryId(registryId, pageable);

        List<ImageResponse> images = new ArrayList<ImageResponse>();

        imgProperties.stream().forEach(imgProperty ->
        {
            try
            {
                byte[] image = storageService.loadImageAsResource(imgProperty).getInputStream().readAllBytes();
                ImageResponse imgResponse = new ImageResponse(imgProperty, image);

                images.add(imgResponse);
            }
            catch (IOException | URISyntaxException ex)
            {
                throw new RuntimeException(ex);
            }
        });

        return new PageImpl<ImageResponse>(images, pageable, images.size());
    }

    public Resource loadById(UUID imageId) throws IOException, URISyntaxException
    {
        ImageProperties props = imagePropertiesRepository.findById(imageId).orElseThrow();

        return storageService.loadImageAsResource(props);
    }
}
