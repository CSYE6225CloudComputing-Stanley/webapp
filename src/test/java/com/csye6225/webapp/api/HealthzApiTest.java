package com.csye6225.webapp.api;

import com.csye6225.webapp.controller.HealthCheckController;
import com.csye6225.webapp.dao.HealthCheckDAO;
import com.csye6225.webapp.service.HealthCheckService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


@WebMvcTest(HealthCheckController.class)
public class HealthzApiTest {

    private MockMvc mockMvc;

    @Autowired
    public HealthzApiTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @MockitoBean
    private HealthCheckService healthCheckService;

    @MockitoBean
    private HealthCheckDAO healthCheckDAO;

    @MockitoBean
    private EntityManager entityManager;

    @Test
    public void testGetHealthzSuccess() throws Exception {
        mockMvc.perform(get("/healthz"))
                .andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

    @Test
    public void testPostHealthzMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/healthz"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(header().string("Cache-Control", "no-cache"));

    }

    @Test
    public void testDeleteHealthzMethodNotAllowed() throws Exception {
        mockMvc.perform(delete("/healthz"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

    @Test
    public void testPutHealthzMethodNotAllowed() throws Exception {
        mockMvc.perform(put("/healthz"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

    @Test
    public void testPatchHealthzMethodNotAllowed() throws Exception {
        mockMvc.perform(patch("/healthz"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

    @Test
    public void testPayloadNotAllowed() throws Exception {
        mockMvc.perform(get("/healthz")
                .content("test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

    @Test
    public void testPathVariableNotAllowed() throws Exception {
        mockMvc.perform(get("/healthz/test"))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

    @Test
    public void testParameterNotAllowed() throws Exception {
        mockMvc.perform(get("/healthz/test"))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Cache-Control", "no-cache"));
    }

}
