package com.ndr.micro.imageservice.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ndr.micro.imageservice.entity.ImageProperties;

@Service
public class StorageService
{
    private final Path storagePath; 
    private final String fileSeparator;
    
    public StorageService(@Value("${storage.relative-location}") final String relativeLocation)
    {
        this.fileSeparator = System.getProperty("file.separator");
        this.storagePath = 
            Paths.get(Paths.get("").toAbsolutePath().normalize().toString(), relativeLocation).normalize();
    }

    //TODO:: Handle FileAlreadyExists
    public void storeImage(ImageProperties imgProperty, MultipartFile multipartFile) throws IOException, IllegalArgumentException
    {
        String detectedFileType = getRealExtensionFromFile(multipartFile.getInputStream());

        if(!validateExtensionAsJpegOrPng(detectedFileType))
            throw new IllegalArgumentException("Invalid file type");

        String filename = imgProperty.buildFileName();
        String sequencePathString = generatePathFromSequenceString(imgProperty.getFilename());

        Path imagePath = Files.createDirectories(Paths.get(storagePath.toString(), sequencePathString));

        Files.copy(multipartFile.getInputStream(), Paths.get(imagePath.toString(), filename));
    }

    public Resource loadImageAsResource(ImageProperties imgProperty) throws IOException, URISyntaxException
    {
        String filename = imgProperty.buildFileName();
        String sequencePathString = generatePathFromSequenceString(imgProperty.getFilename());

        URI resourceIdentifier = new URI(Paths.get(storagePath.toString(), sequencePathString, filename).toString());
        System.out.println(resourceIdentifier.toString());

        Resource resource = new UrlResource("file",resourceIdentifier.toString());

        if (resource.exists())
            return resource;

        throw new FileNotFoundException();
    }

    public String getRealExtensionFromFile(InputStream fileStream) throws IOException
    {
        Tika tika = new Tika();
        String fileType = tika.detect(fileStream);
        
        return fileType.substring(fileType.lastIndexOf('/') + 1);
    }

    //TODO:: remake
    private String generatePathFromSequenceString(String sequenceString)
    {
        StringBuilder sb = new StringBuilder(sequenceString);

        for (int i = 0; i <= sb.length(); i += 4)
        {
            sb.insert(i, fileSeparator);
        }

        return sb.toString().substring(0, sb.length() - 4);
    }
    
    private boolean validateExtensionAsJpegOrPng(String extension)
    {
        return extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png");
    }
}
