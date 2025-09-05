package com.example.tpo.uade.Xperium.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.server.ResponseStatusException;

import com.example.tpo.uade.Xperium.controllers.auth.AuthenticationRequest;
import com.example.tpo.uade.Xperium.controllers.auth.AuthenticationResponse;
import com.example.tpo.uade.Xperium.controllers.auth.RegisterRequest;
import com.example.tpo.uade.Xperium.controllers.config.JwtService;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final ProveedorRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request)  {
                Optional<Proveedor> proveedorOpt = repository.findByEmail(request.getEmail());

                if (!proveedorOpt.isEmpty()) {
                        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El Email ya esta registrado"); 
                }
                var user = Proveedor.builder()
                                .nombre(request.getNombre())
                                .email(request.getEmail())
                                .telefono(request.getTelefono())
                                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                                .role(request.getRole())
                                .build();

                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }
}
