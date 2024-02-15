package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException  extends RuntimeException{

    private HttpStatus status;
    private String message;

    //Constructors

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
//getters
    public HttpStatus getStatus() {
        return status;
    }
//getters
    @Override
    public String getMessage() {
        return message;
    }
}
