package com.jfms.aaa.dal.repository;

public class DatabaseException extends Exception {
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
