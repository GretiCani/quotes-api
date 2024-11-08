package com.quotes.service.client;

import com.quotes.domain.dto.QuoteDto;
import com.quotes.domain.dto.QuotePage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.quotes.utils.QuoteUtils.generateUrl;

@Service
public class QuoteClient {

    private final RestClient restClient;
    private final String quotesHost;

    public QuoteClient(RestClient restClient, @Value("${urls.quotesPath}") String quotesHost) {
        this.restClient = restClient;
        this.quotesHost = quotesHost;
    }

    public QuoteDto getRandomQuote(){
        return restClient.get()
                .uri(quotesHost.concat("/random"))
                .retrieve()
                .body(QuoteDto.class);
    }

    public QuoteDto getQuoteById(String id){
        return restClient.get()
                .uri(quotesHost.concat("/quotes/{id}"),id)
                .retrieve()
                .body(QuoteDto.class);
    }

    public QuotePage compareQuotes(Map<String, String> searchParams){
        var compareUrl = generateUrl(String.format("%s/quotes",quotesHost),searchParams);
        return restClient.get()
                .uri(compareUrl)
                .retrieve()
                .body(QuotePage.class);
    }


}
