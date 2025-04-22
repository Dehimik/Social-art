package com.dehimik.art.exceptions;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String email) {
        super("Email already exists: " + email);
    }
}