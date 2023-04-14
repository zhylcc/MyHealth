package org.example.dao;

import org.example.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
