package com.quotes.service;

import com.quotes.domain.request.RegisterRequest;

public interface UserService {
    void createUser(RegisterRequest req);

}