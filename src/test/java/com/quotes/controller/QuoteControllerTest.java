package com.quotes.controller;

import com.quotes.BaseIntegrationTest;
import com.quotes.domain.response.QuoteResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class QuoteControllerTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void when_get_quotes_for_registered_user_then_return_random_quotes() throws Exception {
        mockMvc.perform(get("/api/v1/quotes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quoteDto._id").exists())
                .andExpect(jsonPath("$.quoteDto.content").isNotEmpty());
    }

    @Test
    @WithAnonymousUser
    @Sql(scripts = "classpath:db-clean.sql")
    public void when_get_quotes_for_new_user_and_no_top_rated_present_then_return_random_quotes() throws Exception {
        mockMvc.perform(get("/api/v1/quotes"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.quoteDto._id").isNotEmpty(),
                        jsonPath("$.quoteDto.content").isNotEmpty()
                )
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    public void when_get_quotes_for_anonymous_user_and_top_rated_is_present_then_return_top_rated() throws Exception {
        //make quote with id QUOTE_ID top-rated
        mockMvc.perform(post("/api/v1/quotes/{quoteId}/likes",QUOTE_ID))
                .andExpect(status().isNoContent());

        // verify that top-rated is returned instead of random quote
        mockMvc.perform(get("/api/v1/quotes"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.quoteDto._id").value(QUOTE_ID),
                        jsonPath("$.quoteDto.author").value(QUOTE_AUTHOR),
                        jsonPath("$.quoteDto.tags[0]").value(TAG_AGE),
                        jsonPath("$.quoteDto.tags[1]").value(TAG_WISDOM),
                        jsonPath("$._links.byTags").value(LINK_BY_TAGS),
                        jsonPath("$._links.byAuthor").value(LINK_BY_AUTHOR),
                        jsonPath("$._links.likeQuote").value(LINK_LIKE_QUOTE))
                .andDo(print());

    }

    @Test
    @WithAnonymousUser
    public void when_quote_is_find_then_a_quote_comparison_can_be_done() throws Exception {
        var response = mockMvc.perform(get("/api/v1/quotes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        QuoteResponse quoteResponse = objectMapper.readValue(response, QuoteResponse.class);
        mockMvc.perform(get(quoteResponse._links().get(BY_TAGS_KEY)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.count").value(20),
                        jsonPath("$.totalCount").value(Matchers.greaterThanOrEqualTo(55)),
                        jsonPath("$.page").value(Matchers.greaterThanOrEqualTo(1)),
                        jsonPath("$.totalPages").value(Matchers.greaterThanOrEqualTo(28)),

                        jsonPath("$.totalPages").value(Matchers.greaterThanOrEqualTo(28))
                ).andDo(print());

    }



}