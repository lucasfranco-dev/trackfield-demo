package com.trackfield.todolist.controllers;

import com.trackfield.todolist.Services.UserService;
import com.trackfield.todolist.dtos.UserDTO;
import com.trackfield.todolist.dtos.UserResponseDTO;
import com.trackfield.todolist.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAll(){


        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity findUser(@PathVariable String uuid){
        User findedUser = userService.getUserByID(uuid);
        UserResponseDTO response = new UserResponseDTO(findedUser.getId(), findedUser.getFirstName(),
                findedUser.getLastName(), findedUser.getEmail(), findedUser.getUserType());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO data) {
        User novoUsuario = userService.createNewUser(data);
        return ResponseEntity.status(201).body(novoUsuario);
    }
}
