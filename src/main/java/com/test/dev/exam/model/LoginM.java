package com.test.dev.exam.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LoginM implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String user;
    private String password;
    private String sesion; 
    private LocalDateTime timestamp;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LoginM{" + "user=" + user + ", password=" + password + ", sesion=" + sesion + ", timestamp=" + timestamp + '}';
    }
}
