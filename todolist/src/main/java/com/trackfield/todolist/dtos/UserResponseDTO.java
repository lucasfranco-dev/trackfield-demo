package com.trackfield.todolist.dtos;

import com.trackfield.todolist.models.UserType;

public record UserResponseDTO(String id, String firstName, String lastName, String email, UserType userType) {
}
