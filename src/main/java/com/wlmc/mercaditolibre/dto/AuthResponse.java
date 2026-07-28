package com.wlmc.mercaditolibre.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String nombre;
    private String rol;
    private String direccion;
    private String telefono;
    
    public AuthResponse() {
    }

    public AuthResponse(String token, String username, String nombre, String rol) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.rol = rol;
    }

    public AuthResponse(String token, String username, String nombre, String rol,
        String direccion, String telefono) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.rol = rol;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    


}