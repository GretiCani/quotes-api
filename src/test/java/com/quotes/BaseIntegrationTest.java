package com.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = "classpath:db-init.sql")
@Sql(scripts = "classpath:db-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BaseIntegrationTest {

    public static String TEST_ADMIN_CREDENTIAL = "admin";
    public static String TEST_USER_CREDENTIAL = "user";
    public static String QUOTE_ID = "j7W6pP1XiG";
    public static String QUOTE_AUTHOR = "Charles Dickens";
    public static String TAG_WISDOM = "Wisdom";
    public static String TAG_AGE = "Age";
    public static String LINK_BY_TAGS = "http://localhost:8080/api/v1/quotes/compare?tags=Age,Wisdom";
    public static String LINK_LIKE_QUOTE = "http://localhost:8080/api/v1/quotes/j7W6pP1XiG/like";
    public static String LINK_BY_AUTHOR = "http://localhost:8080/api/v1/quotes?author=Charles Dickens";
    public static String LIKE_QUOTE_KEY = "likeQuote";
    public static String BY_AUTHOR_KEY = "byAuthor";
    public static String BY_TAGS_KEY = "byTags";

    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();
}
