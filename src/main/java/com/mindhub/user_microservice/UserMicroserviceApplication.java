package com.mindhub.user_microservice;

import com.mindhub.user_microservice.models.RolType;
import com.mindhub.user_microservice.models.UserModel;
import com.mindhub.user_microservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserMicroserviceApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			UserModel admin = new UserModel("ADMIN", "ulisesfarfan296@gmail.com", passwordEncoder.encode("admin1234"), RolType.ADMIN);
			userRepository.save(admin);
		};
	}
}
