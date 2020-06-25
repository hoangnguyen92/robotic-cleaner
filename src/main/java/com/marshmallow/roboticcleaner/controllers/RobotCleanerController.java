package com.marshmallow.roboticcleaner.controllers;

import com.marshmallow.roboticcleaner.dto.request.RobotCleanInput;
import com.marshmallow.roboticcleaner.dto.response.RobotCleanResult;
import com.marshmallow.roboticcleaner.services.RobotCleanerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/robot")
@RequiredArgsConstructor
public class RobotCleanerController {

    private final RobotCleanerService robotCleanerService;

    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public RobotCleanResult clean(@Valid @RequestBody RobotCleanInput robotCleanInput){
        return robotCleanerService.clean(robotCleanInput);
    }
}
