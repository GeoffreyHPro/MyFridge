package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.command.UserCommand;
import com.example.demo.dto.TokenDto;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/token")
@Api(tags = "Token")
public class TokenController {

    @Autowired
    private JWTUtils jwtUtils;

    private UserService userService;

    public TokenController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get token of account", description = "You get the token with your pseudo and password")
    @PostMapping
    public ResponseEntity<TokenDto> authenticationUser(@RequestBody UserCommand userCommand,
            HttpServletResponse response) {
        String jwt = userService.generateToken(userCommand);
        String role = userService.getRole(userCommand.pseudo());

        ResponseCookie cookie = ResponseCookie.from("authToken", jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(24 * 60 * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        TokenDto tokenDto = new TokenDto(jwt, role);
        return ResponseEntity.status(200).body(tokenDto);
    }

    @Operation(summary = "Get token of account", description = "You get the token with your pseudo and password")
    @DeleteMapping
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/refresh")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Void> getCurrentUser(@CookieValue(name = "authToken", required = false) String token) {
        if (token == null || jwtUtils.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}