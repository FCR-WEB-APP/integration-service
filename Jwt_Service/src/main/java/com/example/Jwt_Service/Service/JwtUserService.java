package com.example.Jwt_Service.Service;

import com.example.Jwt_Service.Entity.UserProfile;

import java.util.List;




public interface JwtUserService {

    public UserProfile create(UserProfile userProfile) ;

    List<UserProfile> getallusers();

    public String verify(UserProfile jwtUser);
}
