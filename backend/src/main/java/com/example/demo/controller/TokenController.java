package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.UserCommand;
import com.example.demo.dto.TokenDto;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/token")
@Api(tags = "Token", description = "Endpoint")
public class TokenController {

    private UserService userService;

    public TokenController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get token of account", description = "You get the token with your pseudo and password")
    @PostMapping
    public ResponseEntity<TokenDto> authenticationUser(@RequestBody UserCommand userCommand) {
        String jwt = userService.generateToken(userCommand);
        String role = userService.getRole(userCommand.pseudo());
        TokenDto tokenDto = new TokenDto(jwt, role);
        return ResponseEntity.status(200).body(tokenDto);
    }
}