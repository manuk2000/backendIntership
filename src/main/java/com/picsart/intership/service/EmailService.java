package com.picsart.intership.service;

import com.picsart.intership.config.security.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final JwtService jwtService;
    @Value("${constants.domain}")
    private String domain;

    @Value("${constants.email-activate-expiration-time}")
    private long EMAIL_ACTIVATE_EXPIRATION_TIME;
    @Value("${constants.email-activate-jwt-key}")
    private String EMAIL_ACTIVATE_JWT_KEY;

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }

    public void sendMessageToEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    //    @Async
    public void sendActivateMessage(String email) {
        String token = jwtService.generateToken(
                email
                , EMAIL_ACTIVATE_EXPIRATION_TIME
                , EMAIL_ACTIVATE_JWT_KEY);
        if (!StringUtils.isEmpty(email)) {
            String message = String.format(
                    "Welcome to TMS. Please, visit next link for activate account: %s/auth/registration?email=%s"
                    , domain
                    , token
            );
            sendMessageToEmail(email, "Activation account", message);
            log.info("send message to email for activate account ");
        }
    }

    public void sendMessageFromResetPassword(String email) {
        String token = jwtService.generateToken(
                email
                , EMAIL_ACTIVATE_EXPIRATION_TIME
                , EMAIL_ACTIVATE_JWT_KEY);
        if (!StringUtils.isEmpty(email)) {
            String message = String.format(
                    "Welcome to TMS. Please, visit next link for resete password: %s/auth/resetPassword?email=%s",
                    domain,
                    token
            );

            sendMessageToEmail(email, "Resete password", message);
        }
    }
}
