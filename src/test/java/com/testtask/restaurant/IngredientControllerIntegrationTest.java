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
public class IngredientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private SupplierService supplierService;

    @Test
    public void shouldReturnTestIngredients() throws Exception {
        IngredientTO ingredientTO = getIngredient("shouldReturnTestIngredients");
        ingredientTO = ingredientService.addIngredient(ingredientTO);
        MvcResult result = mockMvc.perform(get("/ingredient/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<IngredientTO> resultIngredient = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<IngredientTO>>(){});
        assertTrue(resultIngredient.contains(ingredientTO));
    }

    @Test
    public void shouldReturnTestIngredient() throws Exception {
        IngredientTO ingredientTO = getIngredient("shouldReturnTestIngredient");
        ingredientTO = ingredientService.addIngredient(ingredientTO);
        MvcResult result = mockMvc.perform(get("/ingredient/" + ingredientTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        IngredientTO resultIngredient = mapper.readValue(result.getResponse().getContentAsString(), IngredientTO.class);
        assertEquals(ingredientTO, resultIngredient);
    }

    @Test
    public void saveSaveTestIngredient() throws Exception {
        IngredientTO ingredientTO = getIngredient("saveSaveTestIngredient");
        MvcResult result = mockMvc.perform(post("/ingredient/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredientTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        IngredientTO resultIngredient = mapper.readValue(result.getResponse().getContentAsString(), IngredientTO.class);
        ingredientTO.setId(resultIngredient.getId());
        assertEquals(ingredientTO, resultIngredient);
    }

    @Test
    public void editTestIngredient() throws Exception {
        IngredientTO preIngredientTO = getIngredient("editTestIngredient");
        preIngredientTO = ingredientService.addIngredient(preIngredientTO);
        IngredientTO ingredientTO = getIngredient("editTestIngredient2");
        ingredientTO.setId(preIngredientTO.getId());
        MvcResult result = mockMvc.perform(put("/ingredient/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredientTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        IngredientTO resultIngredient = mapper.readValue(result.getResponse().getContentAsString(), IngredientTO.class);
        assertEquals(ingredientTO, resultIngredient);
    }

    @Test
    public void deleteTestIngredient() throws Exception {
        IngredientTO ingredientTO = getIngredient("deleteTestIngredient");
        ingredientTO = ingredientService.addIngredient(ingredientTO);
        mockMvc.perform(delete("/ingredient/" + ingredientTO.getId()))
                .andExpect(status().isOk());
    }

    private IngredientTO getIngredient(String ingredient) {
        IngredientTO ingredientTO = IngredientTO.builder()
                .name(ingredient)
                .supplierIds(Collections.singletonList(getSupplier().getId()))
                .build();
        return ingredientTO;
    }

    private SupplierTO getSupplier() {
        return supplierService.getSupplierById(1);
    }
}