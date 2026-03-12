package com.glaiss.lista.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaiss.core.security.test.GlaissMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@GlaissMockUser
public abstract class ListaControllerTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
