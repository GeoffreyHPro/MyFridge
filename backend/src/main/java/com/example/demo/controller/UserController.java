package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.UserCommand;
import com.example.demo.converter.UserConverter;
import com.example.demo.dto.UserLightDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
@Api(tags = "User", description = "Endpoint")
public class UserController {

    private UserService userService;
    private UserConverter userConverter;

    public UserController(
            UserService userService,
            UserConverter userConverter) {
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @Operation(summary = "Create new user", description = "Create new account with pseudo and password given")
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserCommand userCommand) {
        System.out.println("yes");
        userService.createUser(userCommand.pseudo(), userCommand.password());
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @Operation(summary = "Get user informations", description = "Get user informations given the pseudo")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/{pseudo}")
    public ResponseEntity<UserLightDto> getUser(@PathVariable String pseudo) {
        User newUser = userService.getUserByPseudo(pseudo);
        return ResponseEntity.status(HttpStatus.OK).body(userConverter.applyLight(newUser));
    }
}
