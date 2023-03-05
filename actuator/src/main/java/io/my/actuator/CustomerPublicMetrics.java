package io.my.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPublicMetrics implements PublicMetrics{
    private final CustomerRepository customerRepository;




}
