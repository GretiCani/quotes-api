package com.quotes.domain.exception;

public class BlogApiException extends RuntimeException{
    public BlogApiException(String message) {
        super(message);
    }
}