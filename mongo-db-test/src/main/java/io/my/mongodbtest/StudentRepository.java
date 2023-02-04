package io.my.mongodbtest;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
}
