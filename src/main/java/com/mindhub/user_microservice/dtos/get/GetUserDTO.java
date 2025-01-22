package com.mindhub.user_microservice.dtos.get;

import com.mindhub.user_microservice.models.UserModel;

public class GetUserDTO {
    private final Long id;
    private final String username;
    private final String email;

    public GetUserDTO(UserModel userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
        this.email = userModel.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
