package com.ndr.micro.imageservice.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ndr.micro.imageservice.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController
{
    private final ImageService imageService;

    @GetMapping("{registryId}")
    public ResponseEntity<?> getImageById(@PathVariable UUID registryId)
    {
        return null;
    }

    @PostMapping("/{registryId}")
    public ResponseEntity<?> postImage(@RequestParam MultipartFile image, @PathVariable UUID registryId)
    {
        try{
            var imgProp = imageService.save(image, registryId);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(imgProp);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
