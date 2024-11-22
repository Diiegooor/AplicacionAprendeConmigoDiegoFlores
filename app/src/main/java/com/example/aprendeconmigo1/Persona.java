package com.example.aprendeconmigo1;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties // Ignora propiedades desconocidas en los documentos Firestore
public class Persona {
    private String id;
    private String nombre;
    private String apellido;
    private String edad;

    // Constructor vacío (necesario para Firestore)
    public Persona() {
    }

    // Constructor con parámetros (opcional, para inicializar objetos rápidamente)
    public Persona(String id, String nombre, String apellido, String edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad='" + edad + '\'' +
                '}';
    }
}

