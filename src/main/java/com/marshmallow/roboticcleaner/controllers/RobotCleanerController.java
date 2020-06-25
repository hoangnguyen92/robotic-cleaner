package com.marshmallow.roboticcleaner.controllers;

import com.marshmallow.roboticcleaner.dto.request.RobotCleanInput;
import com.marshmallow.roboticcleaner.dto.response.RobotCleanResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robot")
public class RobotCleanerController {

    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public RobotCleanResult clean(@RequestBody RobotCleanInput robotCleanInput){
        return RobotCleanResult.builder()
                .finalPosition(List.of(1,3))
                .oilPatchesCleaned(1)
                .build();
    }
}
