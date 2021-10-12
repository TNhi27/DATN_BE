package com.okteam.exception;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsersExceptionController {

    @ExceptionHandler(value = UsersException.class)
    public ResponseEntity<Object> userEx(UsersException exception) {
        return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExists.class)
    public ResponseEntity<Object> userexit(UserAlreadyExists exists) {
        return new ResponseEntity<>("Username alredy exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundSomething.class)
    public ResponseEntity<Object> notFound(NotFoundSomething notFoundSomething) {
        return new ResponseEntity<>(notFoundSomething.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DataEmpty.class)
    public ResponseEntity<Object> empty(DataEmpty dataEmpty) {
        return new ResponseEntity<>("Data is blank", HttpStatus.BAD_REQUEST);
    }

   

}