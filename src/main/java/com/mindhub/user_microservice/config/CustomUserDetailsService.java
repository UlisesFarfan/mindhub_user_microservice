package com.mindhub.user_microservice.config;

import com.mindhub.user_microservice.exceptions.GenericException;
import com.mindhub.user_microservice.models.UserModel;
import com.mindhub.user_microservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws GenericException {
        UserModel userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new GenericException("User not found with username: " + username));
        System.out.println(username);
        return new User(userEntity.getEmail(), userEntity.getPassword(), AuthorityUtils.createAuthorityList("USER"));
    }

}