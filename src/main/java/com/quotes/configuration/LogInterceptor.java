package com.quotes.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.*;
import java.util.stream.Collectors;

public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        logger.info("_Rest_| [REQUEST]: [Method: {}] [URI: {}] [Headers: {}] [Body: {}]",
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                getRequestHeaders(requestWrapper),
                requestWrapper.getContentAsString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
        logger.info("_Rest_| [RESPONSE]:  [Status: {}] [Headers: {}] [Body: {}]",
                wrapper.getStatus(),
                getResponseHeaders(wrapper),
                new String(wrapper.getContentAsByteArray()));
        wrapper.copyBodyToResponse();
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        return Collections.list( request.getHeaderNames()).stream()
                .filter(h -> !h.contains("authorization"))
                .collect(Collectors.toMap(header -> header, request::getHeader));
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        return response.getHeaderNames().stream().distinct()
                .collect(Collectors.toMap(header -> header, response::getHeader));
    }

}