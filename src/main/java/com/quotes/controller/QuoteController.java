package com.quotes.controller;

import com.quotes.domain.response.QuotePageResponse;
import com.quotes.domain.response.QuoteResponse;
import com.quotes.service.QuoteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/quotes")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping
    public ResponseEntity<QuoteResponse> getRandomQuotes(){
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }


    @PostMapping("/{id}/likes")
    public ResponseEntity<Void> likeQuote(@PathVariable("id") String id){
        quoteService.likeQuote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compare")
    public ResponseEntity<QuotePageResponse> getSimilarQuote(@RequestParam Map<String, String> searchParams){
        return ResponseEntity.ok(quoteService.compareQuotes(searchParams));
    }


}
