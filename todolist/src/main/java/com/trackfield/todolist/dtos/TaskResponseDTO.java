package com.trackfield.todolist.dtos;

public record TaskResponseDTO(Long id, String title, String description, SimpleUserResponseDTO user) {
}
