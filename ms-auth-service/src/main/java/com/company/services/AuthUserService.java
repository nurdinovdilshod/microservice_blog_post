package com.company.services;

import com.company.configuration.security.JwtService;
import com.company.dtos.TokenRequest;
import com.company.dtos.TokenResponse;
import com.company.dtos.UserActivationDto;
import com.company.dtos.UserCreateDTO;
import com.company.entity.AuthUser;
import com.company.entity.UserSMS;
import com.company.enums.Role;
import com.company.enums.Status;
import com.company.enums.TokenTypes;
import com.company.repositories.AuthUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SMSService smsService;

    public AuthUser createUser(@NonNull UserCreateDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Password and ConfirmPassword must be the same");
        }
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .status(Status.NO_ACTIVE)
                .build();

        log.info("User with username '{}' successfully saved", authUser.getUsername());
        return authUserRepository.save(authUser);
    }

    public AuthUser findByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username '%s' not found".formatted(username)));
    }

    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
        String username = tokenRequest.username();
        String password = tokenRequest.password();
        findByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtService.generateToken(username);
    }

    public boolean validateToken(String token) {
        return jwtService.isValidToken(token, TokenTypes.ACCESS)
                && findByUsername(jwtService.getUsername(token, TokenTypes.ACCESS)).getStatus().equals(Status.ACTIVE);
    }

    public AuthUser activateUser(UserActivationDto dto) {
        AuthUser authUser = findByUsername(dto.username());
        UserSMS userSMS = smsService.findByUserId(authUser.getId());
        if (!Objects.isNull(userSMS) && userSMS.getCode().equals(dto.code())) {
            authUser.setStatus(Status.ACTIVE);
            authUserRepository.save(authUser);
            userSMS.setExpired(true);
            smsService.update(userSMS);
            return authUser;
        }
        throw new RuntimeException("Code is invalid");
    }
}
