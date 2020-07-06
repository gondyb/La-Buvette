package fr.sasvb.labuvette.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.sasvb.labuvette.exception.UserNotFoundException;
import fr.sasvb.labuvette.model.User;
import fr.sasvb.labuvette.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final JWTVerifier verifier;

    private final Algorithm algorithm;

    @Autowired
    public UserService(UserRepository userRepository, JWTVerifier verifier, Algorithm algorithm) {
        this.userRepository = userRepository;
        this.verifier = verifier;
        this.algorithm = algorithm;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with provided username not found in the database")
        );
    }

    @Transactional
    public User loadUserByToken(String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(userRepository::findByUsername)
                .orElseThrow(() -> new UserNotFoundException("User with provided token not found."));
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch(JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    @Transactional
    public String getToken(User user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(Duration.ofHours(2));
        return JWT
                .create()
                .withIssuer("la-buvette-graphql-api")
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(user.getUsername())
                .sign(algorithm);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return this.loadUserByUsername(currentPrincipalName);
    }
}
