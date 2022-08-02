package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.EmployeeService;
import com.testtask.restaurant.service.PositionService;
import com.testtask.restaurant.transfer.EmployeeTO;
import com.testtask.restaurant.transfer.PositionTO;
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
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void shouldReturnTestEmployees() throws Exception {
        EmployeeTO employeeTO = getEmployee("shouldReturnTestEmployees");
        employeeTO = employeeService.addEmployee(employeeTO);
        MvcResult result = mockMvc.perform(get("/employee/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<EmployeeTO> resultPosition = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<EmployeeTO>>(){});
        assertTrue(resultPosition.contains(employeeTO));
    }

    @Test
    public void shouldReturnTestEmployee() throws Exception {
        EmployeeTO employeeTO = getEmployee("shouldReturnTestEmployee");
        employeeTO = employeeService.addEmployee(employeeTO);
        MvcResult result = mockMvc.perform(get("/employee/" + employeeTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        EmployeeTO resultEmployee = mapper.readValue(result.getResponse().getContentAsString(), EmployeeTO.class);
        assertEquals(employeeTO, resultEmployee);
    }

    @Test
    public void saveSaveTestEmployee() throws Exception {
        EmployeeTO employeeTO = getEmployee("saveSaveTestEmployee");
        MvcResult result = mockMvc.perform(post("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        EmployeeTO resultEmployee = mapper.readValue(result.getResponse().getContentAsString(), EmployeeTO.class);
        employeeTO.setId(resultEmployee.getId());
        assertEquals(employeeTO, resultEmployee);
    }

    @Test
    public void editTestEmployee() throws Exception {
        EmployeeTO preEmployeeTO = getEmployee("editTestEmployee");
        preEmployeeTO = employeeService.addEmployee(preEmployeeTO);
        EmployeeTO employeeTO = getEmployee("editTestEmployee2");
        employeeTO.setId(preEmployeeTO.getId());
        MvcResult result = mockMvc.perform(put("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        EmployeeTO resultEmployee = mapper.readValue(result.getResponse().getContentAsString(), EmployeeTO.class);
        assertEquals(employeeTO, resultEmployee);
    }

    @Test
    public void deleteTestEmployee() throws Exception {
        EmployeeTO employeeTO = getEmployee("deleteTestEmployee");
        employeeTO = employeeService.addEmployee(employeeTO);
        mockMvc.perform(delete("/employee/" + employeeTO.getId()))
                .andExpect(status().isOk());
    }

    private PositionTO getPosition(String position) {
        PositionTO positionTO = PositionTO.builder()
                .name(position)
                .build();
        positionTO = positionService.addPosition(positionTO);
        return positionTO;
    }

    private EmployeeTO getEmployee(String employee) {
        PositionTO positionTO = getPosition(employee);
        EmployeeTO employeeTO = EmployeeTO.builder()
                .name(employee)
                .salary(10)
                .positionId(positionTO.getId())
                .build();
        return employeeTO;
    }
}