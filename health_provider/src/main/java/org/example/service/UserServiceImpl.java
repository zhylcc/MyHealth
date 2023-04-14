package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.PermissionDao;
import org.example.dao.RoleDao;
import org.example.dao.UserDao;
import org.example.pojo.Permission;
import org.example.pojo.Role;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        Set<Role> roleSet = roleDao.findByUserId(user.getId());
        if (roleSet != null && roleSet.size() > 0) {
            for (Role role :
                    roleSet) {
                Set<Permission> permissionSet = permissionDao.findByRoleId(role.getId());
                if (permissionSet != null && permissionSet.size()>0) {
                    role.setPermissions(permissionSet);
                }
            }
            user.setRoles(roleSet);
        }
        return user;
    }

}
