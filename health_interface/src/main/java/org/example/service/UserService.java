package org.example.service;

import org.example.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
