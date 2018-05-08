package com.jfms.aaa.api;

import com.jfms.aaa.dal.repository.NotFoundException;

public class InvalidInputDataException extends RuntimeException {
    public InvalidInputDataException(NotFoundException e) {
        super(e);
    }

    public InvalidInputDataException() {
    }
}
