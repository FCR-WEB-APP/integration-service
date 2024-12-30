package com.example.Jwt_Service.Service;

import com.example.Jwt_Service.Entity.UserPrinciple;
import com.example.Jwt_Service.Entity.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT * FROM user_profile WHERE name = ?";
        UserProfile userProfile;

        try {
            userProfile = jdbcTemplate.queryForObject(sql, new UserProfileRowMapper(), username);
        } catch (Exception e) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found");
        }

        if (userProfile == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        // Return the custom UserPrinciple object
        return new UserPrinciple(userProfile);
    }
}
