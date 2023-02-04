package io.my.mongodbtest;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CustomStudentRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<Student> findByEmail(String email) {
        Query query = new Query(
                Criteria.where("email").is(email)
        );
        Flux<Student> studentFlux = mongoTemplate.find(query, Student.class);
        return studentFlux.next();
    }

}
