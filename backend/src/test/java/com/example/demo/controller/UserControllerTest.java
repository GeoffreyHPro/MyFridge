package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.command.UserCommand;
import com.example.demo.configuration.JWTAuthFilter;
import com.example.demo.configuration.SecurityConfig;
import com.example.demo.converter.UserConverter;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;

@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private JWTUtils jwtUtils;

    @MockBean
    private JWTAuthFilter jwtAuthFilter;

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
    public void createUser_shouldSuccess() throws Exception {
        UserCommand userCommand = new UserCommand("user123", "securePassword123");
        User user = mock(User.class);

        Mockito.when(userService.createUser("user123", "securePassword123")).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCommand)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals("", result.getResponse().getContentAsString());

        verify(userService).createUser("user123", "securePassword123");
    }
}
