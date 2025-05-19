package com.trackfield.todolist.dtos;

import com.trackfield.todolist.models.UserType;

public record UserDTO(String name, String email, String password, UserType userType) {
}
