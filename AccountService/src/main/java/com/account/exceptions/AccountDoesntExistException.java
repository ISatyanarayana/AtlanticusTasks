package com.account.exceptions;

public class AccountDoesntExistException extends RuntimeException {

    public AccountDoesntExistException(String msg){
        super(msg);
    }
}
