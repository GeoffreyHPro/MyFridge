package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.command.UserCommand;
import com.example.demo.configuration.SecurityConfig;
import com.example.demo.exception.user.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import(SecurityConfig.class)
@WebMvcTest(TokenController.class)
public class TokenControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @WithMockUser
    @Test
    public void generateToken_ThrowBadCredentialsException() throws Exception {
        UserCommand userCommand = new UserCommand("user123", "securePassword123");

        Mockito.when(userService.generateToken(userCommand)).thenThrow(new BadCredentialsException("Invalid password"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andReturn();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }

    @WithMockUser
    @Test
    public void generateToken_ThrowUserNotFoundException() throws Exception {
        UserCommand userCommand = new UserCommand("user123", "securePassword123");

        Mockito.when(userService.generateToken(userCommand)).thenThrow(new UserNotFoundException());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @WithMockUser
    @Test
    public void generateToken_shouldSuccess() throws Exception {
        UserCommand userCommand = new UserCommand("user123", "securePassword123");
        String jwt = "16541856418654165";

        Mockito.when(userService.generateToken(userCommand)).thenReturn(jwt);
        Mockito.when(userService.getRole("user123")).thenReturn("USER");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String jsonJWT = jsonNode.get("jwt").asText();
        String jsonRole = jsonNode.get("role").asText();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(jwt, jsonJWT);
        assertEquals("USER", jsonRole);

        verify(userService).generateToken(userCommand);
        verify(userService).getRole("user123");
    }
}
