package com.marshmallow.roboticcleaner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marshmallow.roboticcleaner.dto.request.RobotCleanInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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
    void clean1() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPosition").isNotEmpty())
                .andExpect(jsonPath("$.finalPosition").isArray())
                .andExpect(jsonPath("$.finalPosition", hasSize(2)))
                .andExpect(jsonPath("$.finalPosition").value(equalTo(List.of(1,3))))
                .andExpect(jsonPath("$.oilPatchesCleaned").value(equalTo(1)));

    }

    @Test
    @DisplayName("execute cleaning process - Successful")
    void clean2() throws Exception {
        RobotCleanInput input = RobotCleanInput.builder()
                .areaSize(List.of(50, 50))
                .startingPosition(List.of(0,0))
                .oilPatches(List.of(
                        List.of(0, 49),
                        List.of(46, 23),
                        List.of(22, 32),
                        List.of(17, 31),
                        List.of(25, 32),
                        List.of(37, 33)
                ))
                .navigationInstructions("NNESEESWNWWNNESEESWNWWNNESEESWNWW")
                .build();

        mockMvc.perform(
                post("/api/robot/clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPosition").isNotEmpty())
                .andExpect(jsonPath("$.finalPosition").isArray())
                .andExpect(jsonPath("$.finalPosition", hasSize(2)))
                .andExpect(jsonPath("$.finalPosition").value(equalTo(List.of(0,3))))
                .andExpect(jsonPath("$.oilPatchesCleaned").value(equalTo(0)));

    }

    @Test
    @DisplayName("execute cleaning process - Error Invalid Position")
    void invalidPostion() throws Exception {
        RobotCleanInput input = RobotCleanInput.builder()
                .areaSize(List.of(1,1))
                .startingPosition(List.of(0,0))
                .oilPatches(new ArrayList<>())
                .navigationInstructions("N")
                .build();

        mockMvc.perform(
                post("/api/robot/clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Invalid Position " + List.of(0,1)));
    }

    @Test
    @DisplayName("execute cleaning process - Error Invalid Direction")
    void invalidDirection() throws Exception {
        RobotCleanInput input = RobotCleanInput.builder()
                .areaSize(List.of(1,1))
                .startingPosition(List.of(0,0))
                .oilPatches(new ArrayList<>())
                .navigationInstructions("Q")
                .build();

        mockMvc.perform(
                post("/api/robot/clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Invalid Direction Q"));
    }

    @Test
    @DisplayName("execute cleaning process - Empty instructions return same place as starting")
    void emptyInstruction() throws Exception {
        RobotCleanInput input = RobotCleanInput.builder()
                .areaSize(List.of(1,1))
                .startingPosition(List.of(0,0))
                .oilPatches(List.of(List.of(0,0)))
                .navigationInstructions("")
                .build();

        mockMvc.perform(
                post("/api/robot/clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPosition").isNotEmpty())
                .andExpect(jsonPath("$.finalPosition").isArray())
                .andExpect(jsonPath("$.finalPosition", hasSize(2)))
                .andExpect(jsonPath("$.finalPosition").value(equalTo(List.of(0,0))))
                .andExpect(jsonPath("$.oilPatchesCleaned").value(equalTo(1)));
    }
}
