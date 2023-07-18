package com.example.Bank.execeptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super(message);
    }
}
