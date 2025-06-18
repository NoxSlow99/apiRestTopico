package crud.topic.api.controller;

import crud.topic.api.dto.request.AuthCreateUserRequest;
import crud.topic.api.dto.request.AuthLoginRequest;
import crud.topic.api.dto.response.AuthResponse;
import crud.topic.api.dto.response.LoginResponse;
import crud.topic.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) {
        return new ResponseEntity<>(
                this.authService.create(authCreateUserRequest),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthLoginRequest authLoginRequest) {
        return new ResponseEntity<>(
                this.authService.login(authLoginRequest),
                HttpStatus.OK
        );
    }
}
