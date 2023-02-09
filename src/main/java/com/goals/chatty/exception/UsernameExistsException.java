package com.goals.chatty.exception;

public class UsernameExistsException  extends Exception {

    private static final long serialVersionUID = 1L;

    private static String generateMessage(String username) {
        return "Username " + username + " is already taken";
    }

    public UsernameExistsException(final String username) {
        super(UsernameExistsException.generateMessage(username));
    }
}
