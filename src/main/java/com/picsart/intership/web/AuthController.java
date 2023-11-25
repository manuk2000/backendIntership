package com.picsart.intership.web;

import com.picsart.intership.dto.auth.LoginRequestDTO;
import com.picsart.intership.dto.auth.LoginResponseDTO;
import com.picsart.intership.dto.auth.RegistrationFormDTO;
import com.picsart.intership.dto.auth.ResetPassword;
import com.picsart.intership.service.AuthService;
import com.picsart.intership.utils.MessageResponse;
import com.picsart.intership.validation.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authorization Controller", description = "This controller for authorization")
public class AuthController {
    private final AuthService authService;
    private final ResponseErrorValidation responseErrorValidation;

    @Operation(description = "Signin syte")
    @ApiResponses({
            @ApiResponse(description = "Signin : BindingResult error", responseCode = "400"),
            @ApiResponse(description = "Signin : Bad email or password.", responseCode = "401", content = @Content(
                    schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Signin : succefuly.", responseCode = "200", content = @Content(
                    schema = @Schema(implementation = LoginResponseDTO.class), mediaType = "application/json"))})

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            log.error("Signin : BindingResult error");
            return ResponseEntity.badRequest().build();
        }
        return authService.login(loginRequestDTO);
    }

    @Operation(description = "Send activation link for input user information", responses = @ApiResponse(
            description = "Send message to any email,not chack email", content =
    @Content(schema = @Schema(implementation = MessageResponse.class))))
    @PostMapping("/registerEmail/{email}")
    public ResponseEntity<Object> registerEmail(@PathVariable String email) {
        return authService.registerEmail(email);
    }

    @Operation(description = "Link from email", responses = {
            @ApiResponse(description = "email not found ", responseCode = "400", content = @Content(schema =
            @Schema(implementation = MessageResponse.class))),
            @ApiResponse(description = "email want to registre again ", responseCode = "400", content = @Content(schema =
            @Schema(implementation = MessageResponse.class))),
            @ApiResponse(description = "Registration succesfuly ", responseCode = "201", content = @Content(schema =
            @Schema(implementation = MessageResponse.class)))})
    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody RegistrationFormDTO dto) {
        return authService.registration(dto);
    }

    @Operation(description = "Send Link for reset password ", responses = {
            @ApiResponse(description = "User Not Found", responseCode = "400", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(description = "Aend link to mail successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    })
    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<Object> sendMessageForwardPassword(@PathVariable String email) {
        return authService.sendMessageforwardPassword(email);
    }

    @Operation(description = "Forward password", tags = "Email is token of link message of mail", responses = {
            @ApiResponse(description = "Token is damaged", responseCode = "400", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(description = "Change password successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping("/resetPassword")
    public ResponseEntity<Object> forwardPassword(@RequestBody ResetPassword resetPassword) {
        return authService.forwardPassword(resetPassword);
    }

    @Operation(
            summary = "Refresh Access Token",
            description = "Refreshes the access token using a refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping("/auth/token/refresh/{refreshToken}")
    public ResponseEntity<Object> refrasheToken(@PathVariable("refreshToken") String token) {
        return authService.refreshToken(token);
    }
}
