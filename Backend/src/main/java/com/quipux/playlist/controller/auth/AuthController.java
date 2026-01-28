package com.quipux.playlist.controller.auth;

import com.quipux.playlist.DTO.auth.LoginDTORequest;
import com.quipux.playlist.DTO.auth.LoginDTOResponse;
import com.quipux.playlist.service.auth.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@Valid @RequestBody LoginDTORequest loginDTORequest) {
        LoginDTOResponse response = authService.login(loginDTORequest);
        return ResponseEntity.ok(response);
    }
}