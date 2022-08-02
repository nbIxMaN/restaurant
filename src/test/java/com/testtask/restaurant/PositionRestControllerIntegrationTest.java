package com.testtask.restaurant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.restaurant.service.PositionService;
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
public class PositionRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PositionService positionService;

    @Test
    public void shouldReturnTestPositions() throws Exception {
        PositionTO positionTO = getPositionTO("shouldReturnTestPositions");
        positionTO = positionService.addPosition(positionTO);
        MvcResult result = mockMvc.perform(get("/position/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        List<PositionTO> resultPosition = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<PositionTO>>(){});
        assertTrue(resultPosition.contains(positionTO));
    }

    @Test
    public void shouldReturnTestPosition() throws Exception {
        PositionTO positionTO = getPositionTO("shouldReturnTestPosition");
        positionTO = positionService.addPosition(positionTO);
        MvcResult result = mockMvc.perform(get("/position/" + positionTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        PositionTO resultPosition = mapper.readValue(result.getResponse().getContentAsString(), PositionTO.class);
        assertEquals(positionTO, resultPosition);
    }

    @Test
    public void saveSaveTestPosition() throws Exception {
        PositionTO positionTO = getPositionTO("saveSaveTestPosition");
        MvcResult result = mockMvc.perform(post("/position/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(positionTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        PositionTO resultPosition = mapper.readValue(result.getResponse().getContentAsString(), PositionTO.class);
        positionTO.setId(resultPosition.getId());
        assertEquals(positionTO, resultPosition);
    }

    @Test
    public void editTestPosition() throws Exception {
        PositionTO prePositionTO = getPositionTO("editTestPosition");
        prePositionTO = positionService.addPosition(prePositionTO);
        PositionTO positionTO = getPositionTO("editTestPosition2");
        positionTO.setId(prePositionTO.getId());
        MvcResult result = mockMvc.perform(put("/position/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(positionTO)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        PositionTO resultPosition = mapper.readValue(result.getResponse().getContentAsString(), PositionTO.class);
        assertEquals(positionTO, resultPosition);
    }

    @Test
    public void deleteTestPosition() throws Exception {
        PositionTO positionTO = getPositionTO("deleteTestPosition");
        positionTO = positionService.addPosition(positionTO);
        mockMvc.perform(delete("/position/"+positionTO.getId()))
                .andExpect(status().isOk());
    }

    private PositionTO getPositionTO(String position) {
        PositionTO positionTO = PositionTO.builder()
                .name(position)
                .build();
        return positionTO;
    }
}