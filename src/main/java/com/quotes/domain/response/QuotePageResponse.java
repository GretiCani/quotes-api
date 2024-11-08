package com.quotes.domain.response;

import java.util.List;

public record QuotePageResponse(
        Integer count,
        Integer totalCount,
        Integer page,
        Integer totalPages,
        List<QuoteResponse> results
){}