package com.example.demo.controller;

import java.io.File;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.image.ImageNotFoundException;
import com.example.demo.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/image")
@SecurityRequirement(name = "Authorization")
@Api(tags = "Image")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id) {
        return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(imageService.getImage(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @PostMapping(path = "/product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam(value = "file") MultipartFile file,
            @PathParam("id") String id) {

        if (file.isEmpty()) {
            throw new ImageNotFoundException();
        }

        imageService.saveFile(id, file);
        return ResponseEntity.ok("");
    }

    boolean isFile(String pathFile) {
        File file = new File(pathFile);
        return file.exists() && file.isFile();
    }
}
