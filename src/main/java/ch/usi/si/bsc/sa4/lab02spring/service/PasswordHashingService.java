package ch.usi.si.bsc.sa4.lab02spring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHashingService {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static PasswordHashingService instance = null;

    public static PasswordHashingService getInstance() {
        if (instance == null) {
            instance = new PasswordHashingService();
        }
        return instance;
    }

    public String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        return passwordEncoder.encode(password);
    }

    public boolean passwordMatches(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }

}
