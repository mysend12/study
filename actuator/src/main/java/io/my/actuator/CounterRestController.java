package io.my.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CounterRestController {
    private final CounterService counterService;
    private final CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return this.customerRepository.findById(id).map(customer -> {
            String metricName = metricPrefix("meter.customers.read.found");
            this.counterService.increment(metricName);
            return ResponseEntity.ok().body(customer);
        }).orElseGet(() -> {
            String metricName = metricPrefix("meter.customers.read.not-found");
            this.counterService.increment(metricName);
            return ResponseEntity.notFound().build();
        });
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Customer newCustomer) {
        this.customerRepository.save(newCustomer);
        ServletUriComponentsBuilder url = ServletUriComponentsBuilder.fromCurrentRequest();

        URI location = url.path("/{id}").buildAndExpand(newCustomer.id()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.customerRepository.delete(id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(this.customerRepository.findAll());
    }

    protected String metricPrefix(String k) {
        return k;
    }

}
