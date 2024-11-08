package com.quotes.service.impl;

import com.quotes.domain.dto.QuoteDto;
import com.quotes.domain.entity.QuoteEntity;
import com.quotes.domain.response.QuotePageResponse;
import com.quotes.domain.response.QuoteResponse;
import com.quotes.repository.QuoteRepository;
import com.quotes.service.QuoteService;
import com.quotes.service.client.QuoteClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import static com.quotes.utils.QuoteUtils.*;

import java.util.Map;
import java.util.Optional;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteClient quoteClient;
    private final QuoteRepository quoteRepository;
    private final String baseUrl;

    public QuoteServiceImpl(QuoteClient quoteClient, QuoteRepository quoteRepository, @Value("${urls.basePath}") String baseUrl) {
        this.quoteClient = quoteClient;
        this.quoteRepository = quoteRepository;
        this.baseUrl = baseUrl;
    }

    @Override
    public QuoteResponse getRandomQuote() {
        QuoteDto quote = null;
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken){
            var topRatedQuote = quoteRepository.findFirstByIdNotNullOrderByLikesDesc();
            quote = Optional.ofNullable(topRatedQuote).map(top -> quoteClient.getQuoteById(top.getId())).orElse(null);
        }
        quote = Optional.ofNullable(quote).orElse(quoteClient.getRandomQuote());
        var links = buildQuoteLinks(quote,baseUrl);
        return new QuoteResponse(quote,links) ;
    }

    @Override
    public void likeQuote(String id) {
        var result = quoteRepository.findById(id);
        result.ifPresentOrElse(q -> {
            q.setLikes(q.getLikes()+1);
            quoteRepository.save(q);
        }, ()-> {
            var quote = quoteClient.getQuoteById(id);
            quoteRepository.save(QuoteEntity.builder().id(quote.id()).likes(1).build());
        });
    }

    public QuotePageResponse compareQuotes(Map<String, String> searchParams){
        var comparePage = quoteClient.compareQuotes(searchParams);
        var quotesWithLink = comparePage.results().stream().map(q -> new QuoteResponse(q,buildQuoteLinks(q,baseUrl))).toList();
        return new QuotePageResponse(comparePage.count(),comparePage.totalCount(),comparePage.page(), comparePage.totalPages(), quotesWithLink);
    }


}