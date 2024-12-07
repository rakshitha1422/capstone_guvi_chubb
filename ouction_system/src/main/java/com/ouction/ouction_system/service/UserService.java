package com.ouction.ouction_system.service;

import com.ouction.ouction_system.Repositery.UserRepositery;
import com.ouction.ouction_system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepositery repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Map<String, String> userVerificationCodes = new HashMap<>();

    public String addUser(User user)
    {

        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setUseremail(user.getUseremail());
        String rawpassword=user.getUserpassword();
        if (rawpassword == null || rawpassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String hashedPassword = passwordEncoder.encode(rawpassword);
        user1.setUserpassword(hashedPassword);
        user1.setUserrole(user.getUserrole());
        user1.setUserverify(user.isUserverify());
        user1.setUserdatejoined(LocalDate.now());


        repo.save(user1);

        return user.getUsername();
    }


    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
