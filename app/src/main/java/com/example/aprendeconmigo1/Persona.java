package com.example.aprendeconmigo1;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties // Ignora propiedades desconocidas en los documentos Firestore
public class Persona {
    private String id;
    private String nombre;
    private String apellido;
    private String edad;
    private String contrasena;

    public Persona(String id, String nombre, String apellido, String edad, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEdad() {
        return edad;
    }

    public String getContrasena() {
        return contrasena;
    }
}


