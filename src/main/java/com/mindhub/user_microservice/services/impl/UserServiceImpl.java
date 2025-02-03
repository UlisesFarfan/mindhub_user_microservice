package com.mindhub.user_microservice.services.impl;

import com.mindhub.user_microservice.dtos.get.GetUserDTO;
import com.mindhub.user_microservice.dtos.post.RegisterUserDTO;
import com.mindhub.user_microservice.dtos.update.UpdateUserDTO;
import com.mindhub.user_microservice.exceptions.GenericException;
import com.mindhub.user_microservice.models.RolType;
import com.mindhub.user_microservice.models.UserModel;
import com.mindhub.user_microservice.repositories.UserRepository;
import com.mindhub.user_microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GetUserDTO create(RegisterUserDTO user) throws GenericException {
        try {
            UserModel userModel = new UserModel(user.username(), user.email(), passwordEncoder.encode(user.password()), RolType.USER);
            UserModel savedUser = userRepository.save(userModel);
            String purl = "http://localhost:8080/api/producer_rabbit/welcome";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", savedUser.getEmail());
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
            restTemplate.exchange(purl, HttpMethod.POST, request, Map.class);
            return new GetUserDTO(savedUser);
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public UserModel save(UserModel user) throws GenericException {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public List<UserModel> getAll() throws GenericException {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public UserModel getById(Long id) throws GenericException {
        return userRepository.findById(id).orElseThrow(() -> new GenericException("user not found"));
    }

    @Override
    public GetUserDTO getDTOById(Long id) throws GenericException {
        try {
            return new GetUserDTO(getById(id));
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public GetUserDTO update(Long id, UpdateUserDTO user) throws GenericException {
        try {
            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new GenericException("user not found"));
            userModel.setUsername(user.username());
            userModel.setEmail(user.email());
            userModel = userRepository.save(userModel);
            return new GetUserDTO(userModel);
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws GenericException {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
