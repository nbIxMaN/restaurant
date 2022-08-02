package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.DishCategoryService;
import com.testtask.restaurant.transfer.DishCategoryTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
public class DishCategoryRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DishCategoryService dishCategoryService;

    @Test
    public void shouldReturnTestDishCategories() throws Exception {
        DishCategoryTO dishCategoryTO = DishCategoryTO.builder()
                .name("shouldReturnTestDishCategories")
                .build();
        dishCategoryTO = dishCategoryService.addDishCategory(dishCategoryTO);
        MvcResult result = mockMvc.perform(get("/dish_category/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<DishCategoryTO> resultDishCategory = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<DishCategoryTO>>(){});
        assertTrue(resultDishCategory.contains(dishCategoryTO));
    }

    @Test
    public void shouldReturnTestDishCategory() throws Exception {
        DishCategoryTO dishCategoryTO = DishCategoryTO.builder()
                .name("shouldReturnTestDishCategory")
                .build();
        dishCategoryTO = dishCategoryService.addDishCategory(dishCategoryTO);
        MvcResult result = mockMvc.perform(get("/dish_category/" + dishCategoryTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        DishCategoryTO resultDishCategory = mapper.readValue(result.getResponse().getContentAsString(), DishCategoryTO.class);
        assertEquals(dishCategoryTO, resultDishCategory);
    }

    @Test
    public void saveSaveTestDishCategory() throws Exception {
        DishCategoryTO dishCategoryTO = DishCategoryTO.builder().name("saveSaveTestDishCategory").build();
        MvcResult result = mockMvc.perform(post("/dish_category/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dishCategoryTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        DishCategoryTO resultDishCategory = mapper.readValue(result.getResponse().getContentAsString(), DishCategoryTO.class);
        dishCategoryTO.setId(resultDishCategory.getId());
        assertEquals(dishCategoryTO, resultDishCategory);
    }

    @Test
    public void editTestDishCategory() throws Exception {
        DishCategoryTO dishCategoryTO = DishCategoryTO.builder()
                .name("shouldReturnTestDishCategory1")
                .build();
        dishCategoryTO = dishCategoryService.addDishCategory(dishCategoryTO);
        dishCategoryTO = DishCategoryTO.builder()
                .id(dishCategoryTO.getId())
                .name("shouldReturnTestDishCategory2")
                .build();
        MvcResult result = mockMvc.perform(put("/dish_category/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dishCategoryTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        DishCategoryTO resultDishCategory = mapper.readValue(result.getResponse().getContentAsString(), DishCategoryTO.class);
        assertEquals(dishCategoryTO, resultDishCategory);
    }

    @Test
    public void deleteTestDishCategory() throws Exception {
        DishCategoryTO dishCategoryTO = DishCategoryTO.builder()
                .name("deleteTestDishCategory")
                .build();
        dishCategoryTO = dishCategoryService.addDishCategory(dishCategoryTO);
        mockMvc.perform(delete("/dish_category/"+dishCategoryTO.getId()))
                .andExpect(status().isOk());
    }
}