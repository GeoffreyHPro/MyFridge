package com.example.demo.exception.image;

import com.example.demo.exception.ResourceNotFoundException;

public class ImageNotFoundException extends ResourceNotFoundException {
    public ImageNotFoundException() {
        super("File not found");
    }
}