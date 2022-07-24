package com.example.myMarket.service;

import com.example.myMarket.model.CreateUser;
import com.example.myMarket.model.Users;

import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();

    void createUser(CreateUser users);

    Users getUser(int id);

    void deleteUser(int id);

    boolean validateUsername(CreateUser users);
}