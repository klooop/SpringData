package com.example.springdata.services;

import com.example.springdata.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
}
