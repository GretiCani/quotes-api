package com.quotes.controller;

import com.quotes.BaseIntegrationTest;
import com.quotes.domain.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseIntegrationTest {

    @Test
    public void shouldVerifyThatUserWithRoleAdminExist() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login").with(httpBasic(TEST_ADMIN_CREDENTIAL,TEST_ADMIN_CREDENTIAL)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "classpath:db-clean.sql")
    public void shouldVerifyThatBadCredentials() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login").with(httpBasic(TEST_ADMIN_CREDENTIAL,"wrongPassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(scripts = "classpath:db-clean.sql")
    public void shouldVerifyThatUserWithRoleUserIsCreated() throws Exception {
        var request = new RegisterRequest(TEST_USER_CREDENTIAL,TEST_USER_CREDENTIAL);
        mockMvc.perform(post("/api/v1/auth/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldVerifyThatUserWithRoleUserCanBeLogged() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login").with(httpBasic(TEST_USER_CREDENTIAL,TEST_USER_CREDENTIAL)))
                .andExpect(status().isOk());
    }


}
