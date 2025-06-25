package com.trackfield.todolist.dtos.user;

import com.trackfield.todolist.dtos.store.SimpleStoreResponseDTO;
import com.trackfield.todolist.models.user.UserType;

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
