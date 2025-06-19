package com.trackfield.todolist.dtos.task;

import com.trackfield.todolist.dtos.user.SimpleUserResponseDTO;

public record TaskResponseDTO(Long id, String title, String description, SimpleUserResponseDTO user) {
}
