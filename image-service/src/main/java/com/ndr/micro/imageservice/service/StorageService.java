package com.ndr.micro.imageservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
//TODO: Refatorate constants
public class StorageService
{
    private final Path storagePath; 
    private final String fileSeparator;
    
    public StorageService(  @Value("${storage.relative-location}") final String relativeLocation)
    {
        this.storagePath = Paths.get(Paths.get("").toAbsolutePath().normalize().toString(), relativeLocation)
                .normalize();
        this.fileSeparator = System.getProperty("file.separator");
    }

    public void storeImage(String sequenceNumber, MultipartFile multipartFile) throws IOException
    {
        String sequencePathString = generatePathFromSequenceString(sequenceNumber);
        String filename = normalizeName(sequenceNumber) + getExtensionWithDot(multipartFile.getOriginalFilename());
        
        Path imagePath = Files.createDirectories(Paths.get(storagePath.toString(), sequencePathString));

        Files.copy(multipartFile.getInputStream(), Paths.get(imagePath.toString(), filename));
    }
    
    private String getExtensionWithDot(String filename)
    {
        return '.' + filename.substring(filename.lastIndexOf('.') + 1);
    }
    
    private String normalizeName(String sequenceString)
    {
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

    private String generatePathFromSequenceString(String sequenceString)
    {
        StringBuilder sb = new StringBuilder(normalizeName(sequenceString));

        for (int i = 0; i <= sb.length(); i += 4)
        {
            sb.insert(i, fileSeparator);
        }
        
        return sb.toString().substring(0, sb.length() - 4);
    }
}
