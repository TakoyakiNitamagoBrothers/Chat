package com.example.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.service.LoginService;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    public void testLoginSuccess() throws Exception {
        String name = "testuser";
        String password = "testpassword";

        given(loginService.authenticate(name, password)).willReturn(true);

        mockMvc.perform(post("/login")
            .param("name", name)
            .param("password", password))
            .andExpect(status().isOk())
            .andExpect(view().name("rooms"))
            .andExpect(request().sessionAttribute("loginUser", name));
    }

    @Test
    public void testLoginFailure() throws Exception {
        String name = "testuser";
        String password = "wrongpassword";

        given(loginService.authenticate(name, password)).willReturn(false);

        mockMvc.perform(post("/login")
            .param("name", name)
            .param("password", password))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login?error"));
    }
}