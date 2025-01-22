package com.mindhub.user_microservice;

import com.mindhub.user_microservice.models.RolType;
import com.mindhub.user_microservice.models.UserModel;
import com.mindhub.user_microservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			UserModel admin = new UserModel("ADMIN", "admin1234", "admin@admin.com", RolType.ADMIN);
			userRepository.save(admin);
			UserModel user = new UserModel("USER", "user1234", "user@user.com", RolType.USER);
			userRepository.save(user);
		};
	}
}
