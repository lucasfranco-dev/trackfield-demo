package com.trackfield.todolist.dtos;

import com.trackfield.todolist.models.UserType;

import java.util.List;

public record UserResponseDTO(String cpf, String firstName, String lastName, String email, UserType userType, List<SimpleTaskResponseDTO> tasks) {
}
