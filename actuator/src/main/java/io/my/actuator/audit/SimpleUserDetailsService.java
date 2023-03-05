package io.my.actuator.audit;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class SimpleUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    private final Set<String> users = new ConcurrentSkipListSet<>();

    public SimpleUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.users.addAll(
                Arrays.asList("A", "B", "C", "D", "E")
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.users.contains(username) ? username : null)
                .map(x -> new User(x, passwordEncoder.encode("password"), AuthorityUtils.createAuthorityList("ROLE_USER")))
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find " + username + "!"))
                ;
    }
}
