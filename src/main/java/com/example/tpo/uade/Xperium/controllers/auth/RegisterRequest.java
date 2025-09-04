package com.example.tpo.uade.Xperium.controllers.auth;

import com.example.tpo.uade.Xperium.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String nombre;
    private String email;
    private String telefono;
    private String contrasenia;
    private Role role;
}
