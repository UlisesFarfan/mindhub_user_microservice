package com.mindhub.user_microservice.services;

import com.mindhub.user_microservice.dtos.get.GetUserDTO;
import com.mindhub.user_microservice.dtos.post.PostUserDTO;
import com.mindhub.user_microservice.dtos.post.RegisterUserDTO;
import com.mindhub.user_microservice.dtos.update.UpdateUserDTO;
import com.mindhub.user_microservice.exceptions.GenericException;
import com.mindhub.user_microservice.models.UserModel;

import java.util.List;

public interface UserService {
    GetUserDTO create(RegisterUserDTO user) throws GenericException;

    UserModel save(UserModel user) throws GenericException;

    List<UserModel> getAll() throws GenericException;

    UserModel getById(Long id) throws GenericException;

    GetUserDTO getDTOById(Long id) throws GenericException;

    GetUserDTO update(Long id, UpdateUserDTO user) throws GenericException;

    void delete(Long id) throws GenericException;
}
