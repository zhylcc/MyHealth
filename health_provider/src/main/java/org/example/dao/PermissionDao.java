package org.example.dao;

import org.example.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    public Set<Permission> findByRoleId(int roleId);
}
