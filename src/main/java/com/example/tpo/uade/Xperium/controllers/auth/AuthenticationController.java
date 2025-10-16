package com.example.tpo.uade.Xperium.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tpo.uade.Xperium.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register/comprador")
    public ResponseEntity<AuthenticationResponse> registerComprador(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.registerComprador(request));
    }
    @PostMapping("/register/proveedor")
    public ResponseEntity<AuthenticationResponse> registerProveedor(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.registerProveedor(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}