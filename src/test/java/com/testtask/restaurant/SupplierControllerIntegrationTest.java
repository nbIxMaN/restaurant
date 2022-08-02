package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.IngredientService;
import com.testtask.restaurant.service.SupplierService;
import com.testtask.restaurant.transfer.IngredientTO;
import com.testtask.restaurant.transfer.SupplierTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class SupplierControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void shouldReturnTestSuppliers() throws Exception {
        SupplierTO supplierTO = getSupplier("shouldReturnTestSuppliers");
        supplierTO = supplierService.addSupplier(supplierTO);
        MvcResult result = mockMvc.perform(get("/supplier/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<SupplierTO> resultSupplier = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<SupplierTO>>(){});
        assertTrue(resultSupplier.contains(supplierTO));
    }

    @Test
    public void shouldReturnTestSupplier() throws Exception {
        SupplierTO supplierTO = getSupplier("shouldReturnTestSupplier");
        supplierTO = supplierService.addSupplier(supplierTO);
        MvcResult result = mockMvc.perform(get("/supplier/" + supplierTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        SupplierTO resultSupplier = mapper.readValue(result.getResponse().getContentAsString(), SupplierTO.class);
        assertEquals(supplierTO, resultSupplier);
    }

    @Test
    public void saveSaveTestSupplier() throws Exception {
        SupplierTO supplierTO = getSupplier("saveSaveTestSupplier");
        MvcResult result = mockMvc.perform(post("/supplier/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(supplierTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        SupplierTO resultSupplier = mapper.readValue(result.getResponse().getContentAsString(), SupplierTO.class);
        supplierTO.setId(resultSupplier.getId());
        assertEquals(supplierTO, resultSupplier);
    }

    @Test
    public void editTestSupplier() throws Exception {
        SupplierTO preSupplierTO = getSupplier("editTestSupplier");
        preSupplierTO = supplierService.addSupplier(preSupplierTO);
        SupplierTO supplierTO = getSupplier("editTestSupplier2");
        supplierTO.setId(preSupplierTO.getId());
        MvcResult result = mockMvc.perform(put("/supplier/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(supplierTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        SupplierTO resultSupplier = mapper.readValue(result.getResponse().getContentAsString(), SupplierTO.class);
        assertEquals(supplierTO, resultSupplier);
    }

    @Test
    public void deleteTestSupplier() throws Exception {
        SupplierTO supplierTO = getSupplier("deleteTestSupplier");
        supplierTO = supplierService.addSupplier(supplierTO);
        mockMvc.perform(delete("/supplier/" + supplierTO.getId()))
                .andExpect(status().isOk());
    }

    private SupplierTO getSupplier(String supplier) {
        SupplierTO supplierTO = SupplierTO.builder()
                .name(supplier)
                .ingredientIds(Collections.singletonList(getIngredient().getId()))
                .build();
        return supplierTO;
    }

    private IngredientTO getIngredient() {
        return ingredientService.getIngredientById(1);
    }
}