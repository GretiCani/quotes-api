package com.quotes.domain.response;

import com.quotes.domain.dto.QuoteDto;

import java.util.Map;

public record QuoteResponse(QuoteDto quoteDto, Map<String,String> _links) { }
