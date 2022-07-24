package com.example.myMarket.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateUser {

    @NotBlank(message = "укажите иля")
    private String username;

    @Size(min = 4, message = "4 or more")
    private String password;

    public CreateUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
