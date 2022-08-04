package ru.clevertec.checkrunner.exception;

public class StorageException extends RuntimeException {
    private String id;

    public StorageException(String message) {
        super(message);        }


    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

}