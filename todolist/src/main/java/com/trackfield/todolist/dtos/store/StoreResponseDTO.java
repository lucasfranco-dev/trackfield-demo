package com.trackfield.todolist.dtos.store;

import com.trackfield.todolist.dtos.user.OwnerResponseDTO;
import com.trackfield.todolist.dtos.user.SellerResponseDTO;
import com.trackfield.todolist.dtos.user.SimpleUserResponseDTO;

public record StoreResponseDTO(
        String id,
        String storeName,
        SimpleUserResponseDTO owner,
        String street,
        String city,
        String state,
        String postalCode
) {
}
