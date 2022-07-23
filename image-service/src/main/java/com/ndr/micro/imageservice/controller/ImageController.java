package com.ndr.micro.imageservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.ndr.micro.imageservice.entity.ImageProperties.ImageResponse;
import com.ndr.micro.imageservice.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController
{
    private final ImageService imageService;

    //TODO:: return ImageResponse
    @GetMapping("/get/{imageId}")
    public ResponseEntity<?> loadImageById(@PathVariable UUID imageId) throws IOException, URISyntaxException
    {
        Resource image = imageService.loadById(imageId);

        HttpHeaders headers = new HttpHeaders();
        String filename = image.getFilename();
        System.out.println(filename);
        if (checkMediaType(filename, "jpeg") || checkMediaType(filename, "jpg"))
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
    
        else if (checkMediaType(filename, "png"))
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
    
        else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();

        headers.add("Image-Id", imageId.toString());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image);
    }

    @GetMapping("/get-by-registry/{registryId}")
    public ResponseEntity<?> getImageByRegistryId(@PathVariable UUID registryId, Pageable pageable)
    {
        Page<ImageResponse> image = imageService.loadByRegistryId(registryId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @PostMapping("/post/{registryId}")
    public ResponseEntity<?> postImage(@RequestParam MultipartFile image, @PathVariable UUID registryId) throws MultipartException
    {
        try
        {
            var imgProp = imageService.save(image, registryId);

            return ResponseEntity.status(HttpStatus.CREATED).body(imgProp);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    private boolean checkMediaType(String filename, String extension)
    {
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);

        return fileExtension.equals(extension);
    }
}
