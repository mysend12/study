package io.my.actuator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepository {
    public Optional<Customer> findById(Long id) {
        return Optional.of(new Customer(id));
    }

    public void save(Customer newCustomer) {

    }

    public void delete(Long id) {

    }

    public List<Customer> findAll() {
        return List.of(new Customer(1L));
    }
}
