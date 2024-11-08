package com.quotes.service;

import com.quotes.domain.dto.QuotePage;
import com.quotes.domain.response.QuotePageResponse;
import com.quotes.domain.response.QuoteResponse;

import java.util.Map;

public interface QuoteService {
    QuoteResponse getRandomQuote();

    void likeQuote(String id);
    QuotePageResponse compareQuotes(Map<String, String> searchParams);
}
