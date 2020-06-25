package com.marshmallow.roboticcleaner.services;

import com.marshmallow.roboticcleaner.dto.request.RobotCleanInput;
import com.marshmallow.roboticcleaner.dto.response.RobotCleanResult;
import com.marshmallow.roboticcleaner.exceptions.InvalidDirectionException;
import com.marshmallow.roboticcleaner.exceptions.InvalidPositionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RobotCleanerService {

    public RobotCleanResult clean(RobotCleanInput robotCleanInput) {
        int[][] seaArea = initSeaArea(robotCleanInput.getAreaSize(), robotCleanInput.getOilPatches());
        return startCleaning(seaArea, robotCleanInput.getStartingPosition(), robotCleanInput.getNavigationInstructions());
    }

    private int[][] initSeaArea(List<Integer> areaSize, List<List<Integer>> oilPatches) {
        int row = areaSize.get(1);
        int col = areaSize.get(0);
        int[][] seaArea = new int[row][col];

        for(List<Integer> oilPosition: oilPatches){
            validatePosition(row, col, oilPosition);

            seaArea[oilPosition.get(1)][oilPosition.get(0)] = 1;
        }

        return seaArea;
    }

    private RobotCleanResult startCleaning(int[][] seaArea, List<Integer> startingPosition, String navigationInstructions) {
        int row = seaArea.length;
        int col = seaArea[0].length;
        int numOilPatchCleaned = 0;
        List<Integer> finalPosition = startingPosition;

        for (int i = 0; i < navigationInstructions.length(); i++) {
            char direction = navigationInstructions.charAt(i);
            List<Integer> nextPosition = getNextPosition(finalPosition, direction);
            validatePosition(row, col, nextPosition);

            if(seaArea[nextPosition.get(1)][nextPosition.get(0)] == 1){
                numOilPatchCleaned++;
                seaArea[nextPosition.get(1)][nextPosition.get(0)] = 0;
            }
            finalPosition = nextPosition;
        }

        return RobotCleanResult.builder()
                .finalPosition(finalPosition)
                .oilPatchesCleaned(numOilPatchCleaned)
                .build();
    }

    private List<Integer> getNextPosition(List<Integer> currentPosition, char direction) {
        int nextX = currentPosition.get(0);
        int nextY = currentPosition.get(1);

        if(direction == 'N' || direction == 'n'){
            nextY++;
        }else if(direction == 'S' || direction == 's'){
            nextY--;
        }else if(direction == 'W' || direction == 'w'){
            nextX--;
        }else if(direction == 'E' || direction == 'e'){
            nextX++;
        }else{
            //throw InvalidDirectionException
            throw new InvalidDirectionException();
        }

        return List.of(nextX, nextY);
    }


    private void validatePosition(int row, int col, List<Integer> position) {
        Integer x = position.get(0);
        Integer y = position.get(1);

        if(x < 0 || x >= col || y < 0 || y >= row){
            //throw InvalidPositionException
            throw new InvalidPositionException();
        }
    }
}
