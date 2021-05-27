package com.example.practical;

import com.example.practical.json.ClientJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getListTest() throws Exception {
        this.mockMvc.perform(get("/v1/client")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void searchTest() throws Exception {
        this.mockMvc.perform(get("/v1/client").queryParam("query", "Sagar")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(1)
    public void insertSuccessTest() throws Exception {
        String body1 = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Sagar")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("8409114567196")
                .mobileNumber("9510930019")
                .build());
        this.mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON).content(body1)).andDo(print()).andExpect(status().isOk());
        String body2 = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Mehul")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("7509154998085")
                .mobileNumber("9510930050")
                .build());
        this.mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON).content(body2)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void insertInvalidIdNumberTest() throws Exception {
        String body = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Sagar")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("8409114567000")
                .mobileNumber("9510930019")
                .build());
        this.mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void insertExistsTest() throws Exception {
        String body1 = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Sagar")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("8001015009087")
                .mobileNumber("9510930019")
                .build());
        this.mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON).content(body1)).andDo(print()).andExpect(status().isBadRequest());
        String body2 = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Sagar")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("8409114567196")
                .mobileNumber("9510930020")
                .build());
        this.mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON).content(body2)).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    public void updateTest() throws Exception {
        String body = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Sagar")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("8409114567196")
                .mobileNumber("9510930019")
                .build());
        this.mockMvc.perform(put("/v1/client/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void updateExistsTest() throws Exception {
        String body1 = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Sagar")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("7509154998085")
                .mobileNumber("9510930019")
                .build());
        this.mockMvc.perform(put("/v1/client/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(body1)).andDo(print()).andExpect(status().isBadRequest());
        String body2 = objectMapper.writeValueAsString(ClientJson.builder()
                .firstName("Mehul")
                .lastName("Panchasara")
                .physicalAddress("Address")
                .idNumber("7509154998085")
                .mobileNumber("9510930019")
                .build());
        this.mockMvc.perform(put("/v1/client/{id}", 2).contentType(MediaType.APPLICATION_JSON).content(body2)).andDo(print()).andExpect(status().isBadRequest());
    }
}
