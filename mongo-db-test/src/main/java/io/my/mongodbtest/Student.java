package io.my.mongodbtest;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@Document
public class Student {

    @Id
    private final String id;
    private final String firstName;
    private final String lastName;

    @Indexed(unique = true)
    private final String email;
    private final Gender gender;
    private final Address address;
    private final List<String> favouritesSubjects;
    private final BigDecimal totalSpentInBooks;
    private final LocalDateTime created;
}
