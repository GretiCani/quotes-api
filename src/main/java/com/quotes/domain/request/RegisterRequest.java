package com.quotes.domain.request;

import lombok.Data;

public record RegisterRequest (
     String username,
     String password
){}
