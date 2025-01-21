package com.auction.system.service;

import com.auction.system.entity.User;
import com.auction.system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BidService bidService;

    public UserService(UserRepository userRepository, BidService bidService) {
        this.userRepository = userRepository;
        this.bidService = bidService;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found, please register.");
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.getBids().forEach(bidService::delete);
        user.setBids(null);
        user = userRepository.save(user);
        userRepository.delete(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
