package com.company.controllers;

import com.company.dtos.*;
import com.company.entity.AuthUser;
import com.company.entity.UserSMS;
import com.company.rabbitmq.RabbitMQProducer;
import com.company.services.AuthUserService;
import com.company.services.SMSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthUserService authUserService;
    private final SMSService smsService;
    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping("/user/register")
    public ResponseEntity<AuthUser> register(@Valid @RequestBody UserCreateDTO dto) {
        AuthUser user = authUserService.createUser(dto);
        UserSMS sms = smsService.createSMS(user);
        rabbitMQProducer.sendMessage(new MessageSentDto(user.getEmail(), sms.getCode()));
        return ResponseEntity.ok(user);
    }
    @PostMapping("/token/access")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        tokenResponse.setRole(authUserService.findByUsername(tokenRequest.username()).getRole());
        return ResponseEntity.ok(tokenResponse);
    }
    @GetMapping("/token/validate")
    public Boolean validateToken(@RequestParam(name = "token") String token) {
        try {
            return authUserService.validateToken(token);
        } catch (Exception e) {
            log.error("Error while validating token: {}", e.getMessage());
            return false;
        }
    }
    @PutMapping("/user/activate")
    public ResponseEntity<AuthUser> activateUser(@RequestBody UserActivationDto dto) {
        AuthUser authUser = authUserService.activateUser(dto);
        return ResponseEntity.ok(authUser);
    }

}
