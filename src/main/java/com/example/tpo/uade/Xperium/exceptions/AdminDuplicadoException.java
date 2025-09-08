package com.example.tpo.uade.Xperium.exceptions;

public class AdminDuplicadoException extends RuntimeException {
    public AdminDuplicadoException() {
        super("Admin con ese nombre ya existe");
    }
}