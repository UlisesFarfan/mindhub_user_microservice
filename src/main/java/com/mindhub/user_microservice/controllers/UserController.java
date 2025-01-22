package com.mindhub.user_microservice.controllers;

import com.mindhub.user_microservice.dtos.get.GetUserDTO;
import com.mindhub.user_microservice.dtos.post.PostUserDTO;
import com.mindhub.user_microservice.dtos.update.UpdateUserDTO;
import com.mindhub.user_microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<GetUserDTO>> getAll() {
        List<GetUserDTO> userDTOS = userService.getAll()
                .stream()
                .map(GetUserDTO::new)
                .toList();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<GetUserDTO> create(@RequestBody PostUserDTO user){
        if (user.username() == null || user.email() == null || user.password() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDTO> update(@RequestBody UpdateUserDTO user, @PathVariable long id){
        if (user.username() == null || user.email() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
