package com.project.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.DataProvider;
import com.project.persistence.entity.Customer;
import com.project.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers() throws Exception {
        when(this.customerRepository.findAll()).thenReturn(DataProvider.dataMockList());

        this.mockMvc.perform(get("/api/customer/findAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    public void getCustomerById() throws Exception {
        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(DataProvider.dataMockObject()));

        this.mockMvc.perform(get("/api/customer/find/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void saveCustomer() throws Exception {
        Customer newCustomer = DataProvider.dataMockNewObject();

        when(this.customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        String body = this.objectMapper.writeValueAsString(newCustomer);

        this.mockMvc.perform(post("/api/customer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jonny"))
                .andExpect(jsonPath("$.lastName").value("Deep"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Long customerId = 1L;

        doNothing().when(customerRepository).deleteById(customerId);

        mockMvc.perform(delete("/api/customer/delete/{id}", customerId))
                .andExpect(status().isNoContent());

        verify(customerRepository).deleteById(customerId);
    }
}
