package com.trackfield.todolist.dtos.store;

import com.trackfield.todolist.dtos.user.SellerResponseDTO;

public record StoreResponseDTO(
        String id,
        String storeName,
        SellerResponseDTO owner,
        String street,
        String city,
        String state,
        String postalCode
) {
}
