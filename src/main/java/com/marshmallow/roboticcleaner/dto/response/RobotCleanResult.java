package com.marshmallow.roboticcleaner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotCleanResult implements Serializable {
    private List<Integer> finalPosition;
    private int oilPatchesCleaned;
}
