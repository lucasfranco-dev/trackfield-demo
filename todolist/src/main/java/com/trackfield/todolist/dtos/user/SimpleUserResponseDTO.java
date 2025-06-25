package com.trackfield.todolist.dtos.user;

import com.trackfield.todolist.models.user.UserType;

public record SimpleUserResponseDTO(
        String cpf,
        String firstName,
        String lastName,
        UserType userType
) {
}