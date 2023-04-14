package org.example.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.pojo.Permission;
import org.example.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.example.pojo.User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        Set<Role> roleSet = user.getRoles();
        for (Role role :
                roleSet) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissionSet = role.getPermissions();
            for (Permission permission :
                    permissionSet) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new User(username, user.getPassword(), grantedAuthorityList);
    }
}
