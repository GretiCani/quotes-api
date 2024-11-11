package com.quotes.utils;

import com.quotes.domain.dto.QuoteDto;

import java.util.Map;

public class QuoteUtils {

    public static String generateUrl(String baseUrl, Map<String, String> data) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        boolean firstParam = true;

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value != null && value.contains(",")) {
                value = value.replace(",", "|");
            }
            appendParam(urlBuilder, key, value, firstParam);
            firstParam = false;
        }

        return urlBuilder.toString();
    }

    private static void appendParam(StringBuilder urlBuilder, String key, String value, boolean firstParam) {
        if (firstParam) {
            urlBuilder.append("?");
        } else {
            urlBuilder.append("&");
        }
        urlBuilder.append(key).append("=").append(value);
    }

    public static Map<String,String> buildQuoteLinks(QuoteDto quote,String baseUrl){
        return Map.of("byAuthor", String.format("%s/quotes?author=%s",baseUrl,quote.author()),
                "byTags",String.format("%s/quotes/compare?tags=%s",baseUrl,String.join(",", quote.tags())),
                "likeQuote",String.format("%s/quotes/%s/likes",baseUrl,quote.id()));
    }

}
