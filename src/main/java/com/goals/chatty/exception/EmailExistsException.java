package com.goals.chatty.exception;

public class EmailExistsException extends Exception {
    private static String generateMessage(String email) {
        return "Email " + email.toLowerCase() + " is already in use";
    }

    public EmailExistsException(final String email) {
        super(EmailExistsException.generateMessage(email));
    }
}
