package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.DishService;
import com.testtask.restaurant.service.EmployeeService;
import com.testtask.restaurant.service.OrderService;
import com.testtask.restaurant.transfer.DishTO;
import com.testtask.restaurant.transfer.EmployeeTO;
import com.testtask.restaurant.transfer.OrderTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
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
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderService orderService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    @Test
    public void shouldReturnTestOrders() throws Exception {
        OrderTO orderTO = getOrder("2010-01-04 01:32:27 UTC");
        orderTO = orderService.addOrder(orderTO);
        MvcResult result = mockMvc.perform(get("/order/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<OrderTO> resultPosition = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<OrderTO>>(){});
        assertTrue(resultPosition.contains(orderTO));
    }

    @Test
    public void shouldReturnTestOrder() throws Exception {
        OrderTO orderTO = getOrder("2010-01-04 01:32:27 UTC");
        orderTO = orderService.addOrder(orderTO);
        MvcResult result = mockMvc.perform(get("/order/" + orderTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        OrderTO resultOrder = mapper.readValue(result.getResponse().getContentAsString(), OrderTO.class);
        assertEquals(orderTO, resultOrder);
    }

    @Test
    public void saveSaveTestOrder() throws Exception {
        OrderTO orderTO = getOrder("2010-01-04 01:32:27 UTC");
        MvcResult result = mockMvc.perform(post("/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        OrderTO resultOrder = mapper.readValue(result.getResponse().getContentAsString(), OrderTO.class);
        orderTO.setId(resultOrder.getId());
        assertEquals(orderTO, resultOrder);
    }

    @Test
    public void editTestOrder() throws Exception {
        OrderTO preOrderTO = getOrder("2010-01-04 01:32:27 UTC");
        preOrderTO = orderService.addOrder(preOrderTO);
        OrderTO orderTO = getOrder("2010-01-08 01:32:27 UTC");
        orderTO.setId(preOrderTO.getId());
        MvcResult result = mockMvc.perform(put("/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        OrderTO resultOrder = mapper.readValue(result.getResponse().getContentAsString(), OrderTO.class);
        assertEquals(orderTO, resultOrder);
    }

    @Test
    public void deleteTestOrder() throws Exception {
        OrderTO orderTO = getOrder("2010-01-04 01:32:27 UTC");
        orderTO = orderService.addOrder(orderTO);
        mockMvc.perform(delete("/order/" + orderTO.getId()))
                .andExpect(status().isOk());
    }

    private DishTO getDish() {
        return dishService.getDishById(1);
    }

    private EmployeeTO getEmployee() {
        return employeeService.getEmployeeById(1);
    }

    private OrderTO getOrder(String date) throws ParseException {
        OrderTO orderTO = OrderTO.builder()
                .preparationDate(simpleDateFormat.parse(date))
                .serviceDate(simpleDateFormat.parse(date))
                .orderDate(simpleDateFormat.parse(date))
                .dishId(getDish().getId())
                .employeeIds(Collections.singletonList(getEmployee().getId()))
                .build();
        return orderTO;
    }
}