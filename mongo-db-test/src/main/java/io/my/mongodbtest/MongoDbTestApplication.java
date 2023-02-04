package io.my.mongodbtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootApplication
public class MongoDbTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbTestApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(
            StudentRepository repository,
            CustomStudentRepository customStudentRepository
    ) {
        return args -> {
            Address address = Address.builder()
                    .country("Korea")
                    .city("Asia/seoul")
                    .postCode("A16")
                    .build();
            Student student = Student.builder()
                    .firstName("Kim")
                    .lastName("Bosung")
                    .email("mysend@kakao.com")
                    .gender(Gender.MAN)
                    .address(address)
                    .favouritesSubjects(
                            List.of("Computer Science")
                    )
                    .totalSpentInBooks(BigDecimal.TEN)
                    .created(LocalDateTime.now())
                    .build();

            customStudentRepository.findByEmail(student.getEmail())
                    .subscribe(a -> log.info("student.id: {}", a.getId()))
            ;
        };
    }

}
