package com.example.kinoxpbackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(NotfoundException.class)
    protected ResponseEntity<ProblemDetail> HandleNotFound(NotfoundException exception){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Not found");
        pd.setDetail(exception.getMessage());
        pd.setProperty("errorCode", "NOT FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

}
