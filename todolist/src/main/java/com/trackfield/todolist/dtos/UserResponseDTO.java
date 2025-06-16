package com.trackfield.todolist.dtos;

import com.trackfield.todolist.models.UserType;

public record UserResponseDTO(String cpf, String firstName, String lastName, String email, UserType userType) {
}
