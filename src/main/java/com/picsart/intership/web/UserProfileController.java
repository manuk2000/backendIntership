package com.picsart.intership.web;

import com.picsart.intership.entity.UserProfile;
import com.picsart.intership.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/v1")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Operation(
            summary = "Get User Profile",
            description = "Retrieves the user profile based on the user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully", content = @Content(schema = @Schema(implementation = UserProfile.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfileService.getUserProfile(userId);
        if (userProfile.isPresent()) {
            return ResponseEntity.ok(userProfile.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Update User Profile",
            description = "Updates the user profile based on the user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User profile updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfile updatedUserProfile) {
        boolean success = userProfileService.updateUserProfile(userId, updatedUserProfile);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create User",
            description = "Creates a new user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = UserProfile.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfile userProfile) {
        UserProfile createdUserProfile = userProfileService.createUser(userProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserProfile);
    }

    @Operation(
            summary = "Delete User",
            description = "Deletes the user profile based on the user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean success = userProfileService.deleteUser(userId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
