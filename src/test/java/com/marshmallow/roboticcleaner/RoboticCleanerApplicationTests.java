package com.marshmallow.roboticcleaner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marshmallow.roboticcleaner.dto.request.RobotCleanInput;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoboticCleanerApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("execute cleaning process - Successful")
    void clean() throws Exception {
        RobotCleanInput input = RobotCleanInput.builder()
                .areaSize(List.of(5, 5))
                .startingPosition(List.of(1, 2))
                .oilPatches(List.of(
                        List.of(1, 0),
                        List.of(2, 2),
                        List.of(2, 3)
                ))
                .navigationInstructions("NNESEESWNWW")
                .build();

        mockMvc.perform(
                post("/api/robot/clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(jsonPath("$.finalPosition").isNotEmpty())
                .andExpect(jsonPath("$.finalPosition").isArray())
                .andExpect(jsonPath("$.finalPosition", hasSize(2)))
                .andExpect(jsonPath("$.finalPosition").value(equalTo(List.of(1,3))))
                .andExpect(jsonPath("$.oilPatchesCleaned").value(equalTo(1)))
                .andExpect(status().isOk());

    }

}
