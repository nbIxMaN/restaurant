package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.DishCategoryService;
import com.testtask.restaurant.service.DishService;
import com.testtask.restaurant.service.IngredientService;
import com.testtask.restaurant.transfer.DishCategoryTO;
import com.testtask.restaurant.transfer.DishTO;
import com.testtask.restaurant.transfer.IngredientTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;
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
public class DishRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DishCategoryService dishCategoryService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private DishService dishService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    @Test
    public void shouldReturnTestDishes() throws Exception {
        DishTO dishTO = getDish("shouldReturnTestDishes");
        dishTO = dishService.addDish(dishTO);
        MvcResult result = mockMvc.perform(get("/dish/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<DishTO> resultPosition = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<DishTO>>(){});
        assertTrue(resultPosition.contains(dishTO));
    }

    @Test
    public void shouldReturnTestDish() throws Exception {
        DishTO dishTO = getDish("shouldReturnTestDish");
        dishTO = dishService.addDish(dishTO);
        MvcResult result = mockMvc.perform(get("/dish/" + dishTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        DishTO resultDish = mapper.readValue(result.getResponse().getContentAsString(), DishTO.class);
        assertEquals(dishTO, resultDish);
    }

    @Test
    public void saveSaveTestDish() throws Exception {
        DishTO dishTO = getDish("saveSaveTestDish");
        MvcResult result = mockMvc.perform(post("/dish/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dishTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        DishTO resultDish = mapper.readValue(result.getResponse().getContentAsString(), DishTO.class);
        dishTO.setId(resultDish.getId());
        assertEquals(dishTO, resultDish);
    }

    @Test
    public void editTestDish() throws Exception {
        DishTO preDishTO = getDish("editTestDish");
        preDishTO = dishService.addDish(preDishTO);
        DishTO dishTO = getDish("editTestDish2");
        dishTO.setId(preDishTO.getId());
        MvcResult result = mockMvc.perform(put("/dish/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dishTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        DishTO resultDish = mapper.readValue(result.getResponse().getContentAsString(), DishTO.class);
        assertEquals(dishTO, resultDish);
    }

    @Test
    public void deleteTestDish() throws Exception {
        DishTO dishTO = getDish("deleteTestDish");
        dishTO = dishService.addDish(dishTO);
        mockMvc.perform(delete("/dish/" + dishTO.getId()))
                .andExpect(status().isOk());
    }

    private DishCategoryTO getDishCategory(String dishCategory) {
        DishCategoryTO dishCategoryTO = DishCategoryTO.builder()
                .name(dishCategory)
                .build();
        dishCategoryTO = dishCategoryService.addDishCategory(dishCategoryTO);
        return dishCategoryTO;
    }

    private IngredientTO getIngredient(String ingredient) {
        IngredientTO ingredientTO = IngredientTO.builder()
                .name(ingredient)
                .build();
        ingredientTO = ingredientService.addIngredient(ingredientTO);
        return ingredientTO;
    }

    private DishTO getDish(String dish) {
        DishCategoryTO dishCategoryTO = getDishCategory(dish);
        IngredientTO ingredientTO = getIngredient(dish);
        DishTO dishTO = DishTO.builder()
                .name(dish)
                .price(10)
                .dishCategoryIds(Collections.singletonList(dishCategoryTO.getId()))
                .ingredientIds(Collections.singletonList(ingredientTO.getId()))
                .build();
        return dishTO;
    }
}