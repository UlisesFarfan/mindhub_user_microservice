package com.mindhub.user_microservice.dtos.post;

import com.mindhub.user_microservice.models.RolType;

public record PostUserDTO (String username, String email, String password, RolType role) {
}
