package com.mindhub.user_microservice.controllers;


import com.mindhub.user_microservice.config.JwtUtils;
import com.mindhub.user_microservice.dtos.get.GetUserDTO;
import com.mindhub.user_microservice.dtos.post.LoginUserDTO;
import com.mindhub.user_microservice.dtos.post.RegisterUserDTO;
import com.mindhub.user_microservice.models.UserModel;
import com.mindhub.user_microservice.repositories.UserRepository;
import com.mindhub.user_microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginUserDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserModel user = userRepository.findByEmail(authentication.getName()).orElse(null);
        assert user != null;
        String jwt = jwtUtil.createToken(authentication.getName(), user.getRolType(), user.getId());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO newUser) {
        if(newUser.email() == null || newUser.email().isBlank() || newUser.password() == null || newUser.password().isBlank() || newUser.username() == null || newUser.username().isBlank()) {
            return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
        }
        GetUserDTO userDTO = userService.create(newUser);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

}