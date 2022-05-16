package com.example.registrarse;

public class User {
    private String id;
    private String email;
    private String username;
    private String password;
    private String apell;
    private String mascota;
    private String fon;
    private String edad;
    private String imageProfile;
    private String imageCover;
    private String descripcionmascota;
    private String edadmascota;
    private long timestamp;
    private long lastConnection;
    private boolean online;

    public User(){

    }

    public User(String id, String email, String username, String password, String apell, String fon, String edad, String imageProfile,String mascota,String descripcionmascota,String edadmascota,String imageCover, long timestamp, long lastConnection, boolean online) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.apell = apell;
        this.mascota = mascota;
        this.descripcionmascota = descripcionmascota;
        this.edadmascota = edadmascota;
        this.fon = fon;
        this.edad = edad;
        this.imageProfile = imageProfile;
        this.imageCover = imageCover;
        this.timestamp = timestamp;
        this.lastConnection = lastConnection;
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getDescripcionmascota() {
        return descripcionmascota;
    }

    public void setDescripcionmascota(String descripcionmascota) {
        this.descripcionmascota = descripcionmascota;
    }
    public String getEdadmascota(){
        return edadmascota;
    }
    public void setEdadmascota(String edadmascota){
        this.edadmascota=edadmascota;
    }

    public String getApell() {
        return apell;
    }

    public void setApell(String apell) {
        this.apell = apell;
    }

    public String getFon() {
        return fon;
    }

    public void setFon(String fon) {
        this.fon = fon;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public long getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(long lastConnection) {
        this.lastConnection = lastConnection;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
