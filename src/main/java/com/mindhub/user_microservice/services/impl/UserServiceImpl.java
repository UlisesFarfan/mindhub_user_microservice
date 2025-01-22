package com.mindhub.user_microservice.services.impl;

import com.mindhub.user_microservice.dtos.get.GetUserDTO;
import com.mindhub.user_microservice.dtos.post.PostUserDTO;
import com.mindhub.user_microservice.dtos.update.UpdateUserDTO;
import com.mindhub.user_microservice.exceptions.GenericException;
import com.mindhub.user_microservice.models.RolType;
import com.mindhub.user_microservice.models.UserModel;
import com.mindhub.user_microservice.repositories.UserRepository;
import com.mindhub.user_microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public GetUserDTO create(PostUserDTO user) throws GenericException {
        try {
            UserModel userModel = new UserModel(user.username(), user.email(), user.password(), RolType.USER);
            UserModel savedUser = userRepository.save(userModel);
            return new GetUserDTO(savedUser);
        } catch (Exception e) {
            throw new GenericException("something went wrong");
        }
    }

    @Override
    public UserModel save(UserModel user) throws GenericException {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new GenericException("something went wrong");
        }
    }

    @Override
    public List<UserModel> getAll() throws GenericException {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new GenericException("something went wrong");
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
            throw new GenericException("something went wrong");
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
            throw new GenericException("something went wrong");
        }
    }

    @Override
    public void delete(Long id) throws GenericException {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new GenericException("something went wrong");
        }
    }
}
