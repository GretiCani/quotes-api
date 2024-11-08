package com.quotes.domain.dto;

import java.util.List;

public record QuotePage(
        Integer count,
        Integer totalCount,
        Integer page,
        Integer totalPages,
        List<QuoteDto> results
){}
