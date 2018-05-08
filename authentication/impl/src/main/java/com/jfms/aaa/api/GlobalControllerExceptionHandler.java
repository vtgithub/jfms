package com.jfms.aaa.api;

//import com.jfms.aaa.service.TooRequestException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, TooManyRequestException.class})
    public void simpleLog(Exception e){
        System.out.println("some unpredicted exception");
        e.printStackTrace();
        //todo log
//        System.out.println("exception has been occured");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputDataException.class)
    public void badRequestLog(InvalidInputDataException e){
        System.out.println("invalid input data exception");
        e.printStackTrace();
        //todo log
    }
}
