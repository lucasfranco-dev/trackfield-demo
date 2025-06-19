package com.trackfield.todolist.dtos.user;

import com.trackfield.todolist.dtos.store.SimpleStoreResponseDTO;
import com.trackfield.todolist.dtos.store.StoreResponseDTO;
import com.trackfield.todolist.dtos.task.SimpleTaskResponseDTO;
import com.trackfield.todolist.models.Store;
import com.trackfield.todolist.models.UserType;

import java.util.List;

public record OwnerResponseDTO(
        String cpf,
        String firstName,
        String lastName,
        String email,
        UserType userType,
        List<SimpleStoreResponseDTO> ownedStores) implements SellerOrOwnerView
{
}
