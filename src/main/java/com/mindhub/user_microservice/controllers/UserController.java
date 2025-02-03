package com.mindhub.user_microservice.controllers;

import com.mindhub.user_microservice.config.JwtUtils;
import com.mindhub.user_microservice.dtos.get.GetUserDTO;
import com.mindhub.user_microservice.dtos.post.PostUserDTO;
import com.mindhub.user_microservice.dtos.update.UpdateUserDTO;
import com.mindhub.user_microservice.services.AppService;
import com.mindhub.user_microservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppService appService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/all")
    public ResponseEntity<List<GetUserDTO>> getAll() {
        List<GetUserDTO> userDTOS = userService.getAll()
                .stream()
                .map(GetUserDTO::new)
                .toList();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<GetUserDTO> getOneDto(HttpServletRequest request) {
        Long id = appService.getId(request);
        GetUserDTO userDTOS = userService.getDTOById(id);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<GetUserDTO> update(HttpServletRequest request, @RequestBody UpdateUserDTO user){
        Long id = appService.getId(request);
        GetUserDTO userDTOS = userService.getDTOById(id);
        if (user.username() == null || user.email() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(HttpServletRequest request){
        Long id = appService.getId(request);
        GetUserDTO userDTOS = userService.getDTOById(id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
