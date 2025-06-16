package com.trackfield.todolist.dtos;

public record SimpleUserResponseDTO(
        String cpf,
        String firstName,
        String lastName
) {
}