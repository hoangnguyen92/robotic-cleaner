# robotic-cleaner
##Instruction to run the Application

Please got to the root of the project and  execute
```
./mvnw spring-boot:run
```

The application should start in few seconds
and can be accessable at port `8080`

##Endpoint
```$xslt
POST localhost:8080/api/robot/clean
```
Possible status code
```
200, 400, 500
```
Payload
```
{
  "areaSize" : [5, 5],
  "startingPosition" : [1, 2],
  "oilPatches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "navigationInstructions" : "NNESEESWNWW"
}
```

Success Response:
```
{
    "finalPosition": [1,3],
    "oilPatchesCleaned": 1
}
```
Error Response:
```
{
    "message": "error message"
}
```
