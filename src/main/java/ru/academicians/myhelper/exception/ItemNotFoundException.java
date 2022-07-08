package ru.academicians.myhelper.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
