package io.shinmen.springrest.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String message) {
        super(message);

    }
}
