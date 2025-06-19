package com.trackfield.todolist.controllers;

import com.trackfield.todolist.services.UserService;
import com.trackfield.todolist.dtos.user.UserDTO;
import com.trackfield.todolist.dtos.user.SellerResponseDTO;
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
        SellerResponseDTO response = userService.findById(cpf);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO data) {
        User novoUsuario = userService.createNewUser(data);
        return ResponseEntity.status(201).body(novoUsuario);
    }
}
