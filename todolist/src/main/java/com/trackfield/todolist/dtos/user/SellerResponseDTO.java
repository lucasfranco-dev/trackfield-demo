package com.trackfield.todolist.dtos.user;

import com.trackfield.todolist.dtos.task.SimpleTaskResponseDTO;
import com.trackfield.todolist.models.user.UserType;

import java.util.List;

public record SellerResponseDTO(
        String cpf,
        String firstName,
        String lastName,
        String email,
        UserType userType,
        List<SimpleTaskResponseDTO> activeTasks) implements SellerOrOwnerView
{
}
