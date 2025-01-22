package com.mindhub.user_microservice.repositories;

import com.mindhub.user_microservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserModel,Long> {
    Optional<UserModel> findByEmail(String email);
}
