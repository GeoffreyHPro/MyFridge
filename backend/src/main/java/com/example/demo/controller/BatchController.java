package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.BatchCommand;
import com.example.demo.model.User;
import com.example.demo.service.BatchService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

/*@RestController
@RequestMapping("/batch")
@Api(tags = "Batch")
public class BatchController {
    private BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('USER')")
    @PostMapping()
    public ResponseEntity<Object> createProduct(@Valid @RequestBody BatchCommand batchCommand,
            @AuthenticationPrincipal User user) {
        batchService.addBatch(batchCommand, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}*/
