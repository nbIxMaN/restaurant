package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.CheckService;
import com.testtask.restaurant.service.OrderService;
import com.testtask.restaurant.transfer.CheckTO;
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
public class CheckRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CheckService checkService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    @Test
    public void shouldReturnTestChecks() throws Exception {
        CheckTO checkTO = getCheckTO("2010-01-04 01:32:27 UTC");
        checkTO = checkService.addCheck(checkTO);
        MvcResult result = mockMvc.perform(get("/check/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<CheckTO> resultPosition = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<CheckTO>>(){});
        assertTrue(resultPosition.contains(checkTO));
    }

    @Test
    public void shouldReturnTestCheck() throws Exception {
        CheckTO checkTO = getCheckTO("2010-01-04 01:32:27 UTC");
        checkTO = checkService.addCheck(checkTO);
        MvcResult result = mockMvc.perform(get("/check/" + checkTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        CheckTO resultCheck = mapper.readValue(result.getResponse().getContentAsString(), CheckTO.class);
        assertEquals(checkTO, resultCheck);
    }

    @Test
    public void saveSaveTestCheck() throws Exception {
        CheckTO checkTO = getCheckTO("2010-01-04 01:32:27 UTC");
        MvcResult result = mockMvc.perform(post("/check/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(checkTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        CheckTO resultCheck = mapper.readValue(result.getResponse().getContentAsString(), CheckTO.class);
        checkTO.setId(resultCheck.getId());
        assertEquals(checkTO, resultCheck);
    }

    @Test
    public void editTestCheck() throws Exception {
        CheckTO preCheckTO = getCheckTO("2010-01-04 01:32:27 UTC");
        preCheckTO = checkService.addCheck(preCheckTO);
        CheckTO checkTO = getCheckTO("2010-01-07 01:32:27 UTC");
        checkTO.setId(preCheckTO.getId());
        MvcResult result = mockMvc.perform(put("/check/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(checkTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        CheckTO resultCheck = mapper.readValue(result.getResponse().getContentAsString(), CheckTO.class);
        assertEquals(checkTO, resultCheck);
    }

    @Test
    public void deleteTestCheck() throws Exception {
        OrderTO orderTO = getNewOrder();
        CheckTO checkTO = CheckTO.builder()
                .ordersIds(Collections.singletonList(orderTO.getId()))
                .date(simpleDateFormat.parse("2010-01-04 01:32:27 UTC"))
                .build();
        checkTO = checkService.addCheck(checkTO);
        mockMvc.perform(delete("/check/" + checkTO.getId()))
                .andExpect(status().isOk());
    }

    private CheckTO getCheckTO(String date) throws ParseException {
        OrderTO orderTO = getNewOrder();
        CheckTO checkTO = CheckTO.builder()
                .ordersIds(Collections.singletonList(orderTO.getId()))
                .date(simpleDateFormat.parse(date))
                .build();
        return checkTO;
    }

    private OrderTO getNewOrder() {
        OrderTO orderTO = orderService.getOrdersById(1);
        orderTO.setId(0);
        orderTO = orderService.addOrder(orderTO);
        return orderTO;
    }
}