package org.example.dao;

import org.example.pojo.Role;

import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(int userId);
}
