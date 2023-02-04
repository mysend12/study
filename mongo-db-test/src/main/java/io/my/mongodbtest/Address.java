package io.my.mongodbtest;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class Address {
    private final String country;
    private final String city;
    private final String postCode;
}
