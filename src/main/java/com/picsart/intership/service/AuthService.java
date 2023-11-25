package com.picsart.intership.service;

import com.picsart.intership.config.security.JWT.JwtService;
import com.picsart.intership.dto.UserProfileDto;
import com.picsart.intership.dto.auth.LoginRequestDTO;
import com.picsart.intership.dto.auth.LoginResponseDTO;
import com.picsart.intership.dto.auth.RegistrationFormDTO;
import com.picsart.intership.dto.auth.ResetPassword;
import com.picsart.intership.entity.ERole;
import com.picsart.intership.entity.UserProfile;
import com.picsart.intership.utils.MessageResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserProfileService userServices;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final ModelMapper mapper;
    @Value("${constants.email-activate-jwt-key}")
    private String EMAIL_ACTIVATE_JWT_KEY;

    public ResponseEntity<Object> login(LoginRequestDTO loginForm) {
        UserProfile user = userServices.getUserByEmail(loginForm.getEmail());

        if (user == null || user.getPassword().equals(passwordEncoder.encode(loginForm.getPassword()))) {
            log.error("Signin : bad email or password.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Bad email or password."));
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.getEmail()
                , loginForm.getPassword()
        ));

        LoginResponseDTO res = getAccessRefreshTokens(user.getEmail());
        return ResponseEntity.ok(res);
    }

    private LoginResponseDTO getAccessRefreshTokens(String email) {
        String accessToken = jwtService.generateAccessToken(email);
        String refreshToken = jwtService.generateRefreshToken(email);
        log.info("Login is succesfuly");

        LoginResponseDTO res = new LoginResponseDTO()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);
        return res;
    }

    public ResponseEntity<Object> registration(RegistrationFormDTO dto) {
        Optional<String> subjectFromToken = jwtService.extractClaim(dto.getEmail(), EMAIL_ACTIVATE_JWT_KEY, Claims::getSubject);
        if (subjectFromToken.isEmpty()) {
            log.error("Registration : email {} mot found ", dto.getName());
            return new ResponseEntity<>(new MessageResponse("Username is taken!"), HttpStatus.BAD_REQUEST);
        }
        String email = subjectFromToken.get();

        UserProfile user = userServices.getUserByEmail(email);
        if (user != null) {
            log.error("Registration : email {} want to registre again", email);
            return new ResponseEntity<>(new MessageResponse("User have!"), HttpStatus.BAD_REQUEST);
        }


        user = new UserProfile()
                .setActive(true)
                .setName(dto.getName())
                .setEmail(email)
                .setRole(ERole.STUDENT)
                .setPassword(passwordEncoder.encode(dto.getPassword()));
        UserProfile createUser = userServices.createUser(user);

        log.info("Registration : email {}, name {}", email, dto.getName());
        return new ResponseEntity<>(mapper.map(createUser, UserProfileDto.class), HttpStatus.CREATED);

    }

    public ResponseEntity<Object> resetPassword(String email, String newPassword) {
        UserProfile user = userServices.getUserByEmail(email);
        if (null == user) {
            log.error("Email not found on reset password ");
            return new ResponseEntity<>(new MessageResponse("User not found!"), HttpStatus.NOT_FOUND);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userServices.createUser(user);
        log.info("Reset password  +succesfuly");
        return ResponseEntity.ok(new MessageResponse("Change password successfully"));
    }

    public ResponseEntity<Object> sendMessageforwardPassword(String email) {
        if (userServices.getUserByEmail(email) == null) {
            log.error("forwardPassword :User {} Not Found", email);
            return ResponseEntity.notFound().build();
        }
        emailService.sendMessageFromResetPassword(email);
        log.info("forwardPassword :send message to mail successfully");
        return ResponseEntity.ok(new MessageResponse("send message to mail successfully"));
    }


    public ResponseEntity<Object> chackHaveUserByEmail(String email) {
        UserProfile userByEmail = userServices.getUserByEmail(email);
        if (userByEmail != null) {
            log.error("Registration : email {} want to registre again", email);
            return new ResponseEntity<Object>(new MessageResponse("User have!"), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public ResponseEntity<Object> registerEmail(String email) {
        ResponseEntity<Object> messageResponseResponseEntity = chackHaveUserByEmail(email);
        if (messageResponseResponseEntity != null) {
            return messageResponseResponseEntity;
        }
        emailService.sendActivateMessage(email);
        return ResponseEntity.ok(new MessageResponse("send message to mail successfully"));
    }

    public ResponseEntity<Object> forwardPassword(ResetPassword resetPassword) {
        Optional<String> email = jwtService.extractClaim(resetPassword.getEmail(), EMAIL_ACTIVATE_JWT_KEY, Claims::getSubject);
        if (email.isEmpty()) {
            log.error("forward Password : Token is damaged!");
            return new ResponseEntity<>(new MessageResponse("Token is damaged!"), HttpStatus.BAD_REQUEST);
        }
        return resetPassword(email.get(), resetPassword.getNewPassword());
    }

    public ResponseEntity<Object> refreshToken(String token) {
        String email = jwtService.extractRefreshTokenUsername(token);
        if (email != null) {
            return ResponseEntity.ok(getAccessRefreshTokens(token));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalite refrash token"));
        }
    }
}
