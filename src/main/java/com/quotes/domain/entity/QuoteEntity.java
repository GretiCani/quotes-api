package com.quotes.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "quotes")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteEntity extends BaseEntity{

    @Id
    private String id;
    private int likes;
}
