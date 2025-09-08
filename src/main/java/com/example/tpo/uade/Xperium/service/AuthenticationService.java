package com.example.tpo.uade.Xperium.service;

import java.util.Optional;
   
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tpo.uade.Xperium.controllers.auth.AuthenticationRequest;
import com.example.tpo.uade.Xperium.controllers.auth.AuthenticationResponse;
import com.example.tpo.uade.Xperium.controllers.auth.RegisterRequest;
import com.example.tpo.uade.Xperium.controllers.config.JwtService;
import com.example.tpo.uade.Xperium.entity.Admin;
import com.example.tpo.uade.Xperium.entity.Comprador;
import com.example.tpo.uade.Xperium.entity.Proveedor;
import com.example.tpo.uade.Xperium.entity.Role;
import com.example.tpo.uade.Xperium.repository.AdminRepository;
import com.example.tpo.uade.Xperium.repository.CompradorRepository;
import com.example.tpo.uade.Xperium.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ProveedorRepository proveedorRepository;
    private final CompradorRepository compradorRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerComprador(RegisterRequest request) {
        String email = request.getEmail();

        Optional<Comprador> compradorOpt = compradorRepository.findByEmail(email);
        if (compradorOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }
        var comprador = Comprador.builder()
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .email(email)
            .telefono(request.getTelefono())
            .contrasenia(passwordEncoder.encode(request.getContrasenia()))
            .role(Role.COMPRADOR)
            .build();
        compradorRepository.save(comprador);
        var jwtToken = jwtService.generateToken(comprador);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
    }
        

    public AuthenticationResponse registerProveedor(RegisterRequest request) {
        String email = request.getEmail();
        Optional<Proveedor> proveedorOpt = proveedorRepository.findByEmail(email);

        if (proveedorOpt.isPresent()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }
            var proveedor = Proveedor.builder()
                .nombre(request.getNombre())
                .email(email)
                .telefono(request.getTelefono())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .role(Role.VENDEDOR)
                .build();
            proveedorRepository.save(proveedor);
            var jwtToken = jwtService.generateToken(proveedor);
            return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        String email = request.getEmail();
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }
        var admin = Admin.builder()
            .nombre(request.getNombre())
            .email(email)
            .telefono(request.getTelefono())
            .contrasenia(passwordEncoder.encode(request.getContrasenia()))
            .role(Role.ADMIN)
            .build();
        adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getContrasenia()));

        Optional<Proveedor> proveedorOpt = proveedorRepository.findByEmail(request.getEmail());
        if (proveedorOpt.isPresent()) {
            var proveedor = proveedorOpt.get();
            var jwtToken = jwtService.generateToken(proveedor);
            return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
        }

        Optional<Comprador> compradorOpt = compradorRepository.findByEmail(request.getEmail());
        if (compradorOpt.isPresent()) {
            var comprador = compradorOpt.get();
            var jwtToken = jwtService.generateToken(comprador);
            return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
        }

        Optional<Admin> adminOpt = adminRepository.findByEmail(request.getEmail());
        if (adminOpt.isPresent()) {
            var admin = adminOpt.get();
            var jwtToken = jwtService.generateToken(admin);
            return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");
    }
}   