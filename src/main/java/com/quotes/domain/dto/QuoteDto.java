package com.quotes.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record QuoteDto(
        @JsonProperty("_id") String id,
        String content,
        String author,
        List<String> tags,
        String authorSlug,
        Integer length
        ) {}
