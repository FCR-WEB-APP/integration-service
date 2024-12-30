package com.example.Jwt_Service.Service;

import com.example.Jwt_Service.Entity.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;


@Service
public class JwtUserServiceImpl implements JwtUserService {


    private final BCryptPasswordEncoder passwordEncoder;

    private final JdbcTemplate jdbcTemplate;

    public JwtUserServiceImpl(BCryptPasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserProfile create(UserProfile userProfile) {
          String pass = passwordEncoder.encode(userProfile.getPassword());
        String sql = "INSERT INTO user_profile (user_id, name, roles, is_active, password, email) VALUES (?, ?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, userProfile.getUserId(), userProfile.getName(),
                userProfile.getRoles(), userProfile.getIsActive(),
                pass, userProfile.getEmail());
        if (rows > 0) {
            System.out.println("A new user has been inserted.");
        }
        return userProfile;
    }

    @Override
    public List<UserProfile> getallusers() {
        String sql = "SELECT * FROM user_profile";
        return jdbcTemplate.query(sql, new UserProfileRowMapper());
    }

    @Override
    public String verify(UserProfile userProfile) {
        // Fetch the user based on username and verify password
        String sql = "SELECT * FROM user_profile WHERE name = ?";
        UserProfile fetchedUser = jdbcTemplate.queryForObject(sql, new UserProfileRowMapper(), userProfile.getName());

        if (fetchedUser != null && new BCryptPasswordEncoder().matches(userProfile.getPassword(), fetchedUser.getPassword())) {
            return new JwtService().generateToken(userProfile.getName());
        }
        return "fail";
    }
}
class UserProfileRowMapper implements RowMapper<UserProfile> {

    @Override
    public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(rs.getLong("user_id"));
        userProfile.setName(rs.getString("name"));
        userProfile.setRoles(rs.getString("roles"));
        userProfile.setIsActive(rs.getString("is_active").charAt(0));
        userProfile.setPassword(rs.getString("password"));
        userProfile.setEmail(rs.getString("email"));
        return userProfile;
    }
}
