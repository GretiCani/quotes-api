package com.quotes.repository;

import com.quotes.domain.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity,String> {

    QuoteEntity findFirstByIdNotNullOrderByLikesDesc();
}
