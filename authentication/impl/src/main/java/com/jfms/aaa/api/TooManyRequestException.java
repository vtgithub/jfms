package com.jfms.aaa.api;

import com.jfms.aaa.service.TooRequestException;

public class TooManyRequestException extends RuntimeException {
    public TooManyRequestException(TooRequestException e) {
        super(e);
    }

}
