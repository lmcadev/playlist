package com.quipux.playlist.controller;

import com.quipux.playlist.DTO.LoginDTORequest;
import com.quipux.playlist.DTO.LoginDTOResponse;
import com.quipux.playlist.service.AuthService;
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