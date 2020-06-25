package com.marshmallow.roboticcleaner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotCleanInput implements Serializable {

    @Size(max = 2, min = 2, message = "must be exact 2")
    @NotEmpty
    private List<Integer> areaSize;

    @Size(max = 2, min = 2, message = "must be exact 2")
    @NotEmpty
    private List<Integer> startingPosition;

    private List<@Size(max = 2, min = 2, message = "must be exact 2") List<Integer>> oilPatches;

    @NotEmpty
    private String navigationInstructions;
}
