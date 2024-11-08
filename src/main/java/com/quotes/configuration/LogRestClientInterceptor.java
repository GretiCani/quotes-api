package com.quotes.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LogRestClientInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogRestClientInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.info("_RestClient_| [REQUEST]: [Method: {}] [URI: {}] [Headers: {}] [Body: {}]",request.getMethod().name(),
                request.getURI(), request.getHeaders(), new String(body));
        var response = execution.execute(request, body);
        logger.info("_RestClient_| [RESPONSE]:  [Status: {}] [Headers: {}]", response.getStatusText(),
                response.getHeaders());
        return response;
    }

}