package juja.microservices.gamification.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import juja.microservices.gamification.entity.UserAchievementDetails;
import juja.microservices.gamification.entity.UserIdsRequest;
import juja.microservices.gamification.entity.UserPointsSum;
import juja.microservices.gamification.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/gamification/users", produces = "application/json")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/pointSum")
    @ApiOperation(
            value = "Get total points for all users",
            notes = "This method returns total points for all users")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Returns total points for all users"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method")})
    public ResponseEntity<?> getAllUsersWithPointSum() {
        log.debug("Received request /pointSum");
        List<UserPointsSum> users = userService.getAllUsersWithPointSum();
        log.debug("Response data: users with point sum, quantity = {}", users.size());

        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/achievementDetails", consumes = "application/json")
    @ApiOperation(
            value = "Get achievement details for some users",
            notes = "This method returns detailed information for selected users")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Returns full information for selected users"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type")})
    public ResponseEntity<?> getUsersWithAchievementDetails(@RequestBody UserIdsRequest toIds) {
        log.debug("Received request /achievementDetails");
        List<UserAchievementDetails> result = userService.getUserAchievementsDetails(toIds);
        log.debug("Response data: users with achievement details, quantity = {}", result.size());

        return ResponseEntity.ok(result);
    }
}
