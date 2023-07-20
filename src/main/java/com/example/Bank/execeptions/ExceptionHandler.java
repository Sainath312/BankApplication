package com.example.Bank.execeptions;

import com.example.Bank.model.ExceptionModel;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({UserNotFound.class})
    public ResponseEntity<Object> handleUserNotFound(Exception exception, WebRequest res) {
        ExceptionModel ex = new ExceptionModel(HttpStatus.NOT_FOUND.toString(), exception.getMessage(), res.getDescription(false));
        return new ResponseEntity<Object>(ex, HttpStatus.NOT_FOUND);
    }

        @org.springframework.web.bind.annotation.ExceptionHandler({MoneyException.class})
    public ResponseEntity<Object> handleMoneyException(Exception exe,WebRequest res1){
        ExceptionModel ex1 = new ExceptionModel(HttpStatus.BAD_REQUEST.toString(),exe.getMessage(),res1.getDescription(false));
        return new ResponseEntity<Object>(ex1,HttpStatus.BAD_REQUEST);

    }



}
