package com.trackfield.todolist.controllers;

import com.trackfield.todolist.dtos.user.SellerOrOwnerView;
import com.trackfield.todolist.dtos.user.SimpleUserResponseDTO;
import com.trackfield.todolist.services.UserService;
import com.trackfield.todolist.dtos.user.UserDTO;
import com.trackfield.todolist.dtos.user.SellerResponseDTO;
import com.trackfield.todolist.models.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(userService.findAll());
    }



    @GetMapping("/{cpf}")
    public ResponseEntity findUser(@PathVariable String cpf){
        SellerOrOwnerView response = userService.findById(cpf);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping
    public ResponseEntity<SellerOrOwnerView> createUser(@RequestBody @Valid UserDTO data) {
        SellerOrOwnerView createdUserResponse = userService.createNewUser(data);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(data.cpf())
                .toUri();

        return ResponseEntity.created(location).body(createdUserResponse);
    }
}
