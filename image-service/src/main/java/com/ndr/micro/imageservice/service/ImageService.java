package com.ndr.micro.imageservice.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ndr.micro.imageservice.entity.ImageProperties;
import com.ndr.micro.imageservice.repository.ImageRepository;
import com.ndr.micro.imageservice.repository.SequenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService
{
    private final ImageRepository imageRepository;
    private final SequenceRepository seqRepository;
    private final StorageService storageService;

    public ImageProperties save(MultipartFile image, UUID registryId) throws IOException
    {
        ImageProperties imgProps = new ImageProperties(registryId);
        seqRepository.save(imgProps.getSequenceNumber());
        imgProps = imageRepository.save(imgProps);

        try
        {
            storageService.storeImage(imgProps.getSequenceNumber().getNumber().toString(), image);
        }
        catch (IOException ex)
        {
            throw ex;
        }

        return imgProps;
    }
    
    public Resource load(String filename)
    {
        try{
            Path file = Paths.get("").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable())
            {
                return resource;
            } else {
                throw new RuntimeException("");
            }
        } catch (MalformedURLException ex)
        {
            throw new RuntimeException("error");
        }
        
    }
}
