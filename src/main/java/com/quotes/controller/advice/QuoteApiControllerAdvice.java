package com.quotes.controller.advice;

import com.quotes.domain.exception.BlogApiException;
import com.quotes.domain.exception.BlogApiExceptionResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class QuoteApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BlogApiException.class)
    public ResponseEntity<BlogApiExceptionResponse> handleBlogApiException(BlogApiException e) {
        log.error("Error BlogAPiException is thrown with message {}",e.getMessage());
        return ResponseEntity.badRequest().body(new BlogApiExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<BlogApiExceptionResponse> handleBlogApiException(HttpClientErrorException e) {
        var errorMessage = e.getMessage();
        var startIdx = errorMessage.indexOf(":");
        errorMessage = startIdx!=-1?errorMessage.substring(startIdx+1):errorMessage;
        log.error("Error during rest client call with error message {}",errorMessage);
        return ResponseEntity.badRequest().body(new BlogApiExceptionResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }


}