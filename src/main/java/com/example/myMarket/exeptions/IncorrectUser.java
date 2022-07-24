package com.example.myMarket.exeptions;

public class IncorrectUser extends RuntimeException{
    public IncorrectUser(String message) {
        super(message);
    }
}
