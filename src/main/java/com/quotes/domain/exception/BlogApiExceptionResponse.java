package com.quotes.domain.exception;

public record BlogApiExceptionResponse(
        Integer statusCode,
        String errorMessage
) { }