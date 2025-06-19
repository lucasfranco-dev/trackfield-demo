package com.trackfield.todolist.dtos.user;

public record SimpleUserResponseDTO(
        String cpf,
        String firstName,
        String lastName
) {
}