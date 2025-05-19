package com.trackfield.todolist.controllers;

import com.trackfield.todolist.Services.UserService;
import com.trackfield.todolist.dtos.UserDTO;
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
    public ResponseEntity findUser(String uuid){
        User findedUser = userService.getUserByID(uuid);
        return ResponseEntity.ok(findedUser);
    }

    @PostMapping
    public ResponseEntity<User> criar(@RequestBody UserDTO data) {
        User novoUsuario = userService.createNewUser(data);
        return ResponseEntity.status(201).body(novoUsuario);
    }
}
