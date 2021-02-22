package net.jesusramirez.contentprovider.data;

import java.io.Serializable;

public class Usuario implements Serializable {
    private long ID;
    private String nombre;
    private String telefono;
    private String email;
    private String contrasenia;

    public Usuario(String nombre, String email, String contrasenia, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    //constructor para cuando se vaya a validar el usuario
    public Usuario(String email, String password) {
        this.email = email;
        this.contrasenia = password;
    }

    public Usuario(int ID, String nombre, String email, String contrasenia, String telefono) {
        this.ID = ID;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
