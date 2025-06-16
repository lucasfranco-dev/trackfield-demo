package com.trackfield.todolist.dtos;

import java.util.List;

public record UserResponseFindAllDTO(
        String cpf,
        String firstName,
        String lastName,
        List<SimpleTaskResponseDTO> tasks
) {
}