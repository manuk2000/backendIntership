package com.picsart.intership.web;

import com.picsart.intership.entity.UserProfile;
import com.picsart.intership.utils.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(
            summary = "happy server is working",
            description = "This endpoint for test is enable server."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Server enabled successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/hello")
    public ResponseEntity<?> getString() {
        System.out.println();
        return ResponseEntity.ok(new MessageResponse("Hello"));
    }
}
