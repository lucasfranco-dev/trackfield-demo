package com.trackfield.todolist.controllers;

import com.trackfield.todolist.Services.UserService;
import com.trackfield.todolist.dtos.UserDTO;
import com.trackfield.todolist.dtos.SimpleUserResponseDTO;
import com.trackfield.todolist.dtos.UserResponseDTO;
import com.trackfield.todolist.models.User;
import org.springframework.http.HttpStatus;
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



    @GetMapping("/{cpf}")
    public ResponseEntity findUser(@PathVariable String cpf){
        UserResponseDTO response = userService.getUserByID(cpf);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO data) {
        User novoUsuario = userService.createNewUser(data);
        return ResponseEntity.status(201).body(novoUsuario);
    }
}
