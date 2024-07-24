package com.account.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{

    public AccountAlreadyExistsException(String msg){
        super(msg);
    }

}
