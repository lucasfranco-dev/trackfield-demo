package com.trackfield.todolist.dtos.store;

public record StoreDTO(
        String storeName,
        String cpfStoreOwner,
        String street,
        String city,
        String state,
        String postalCode
) {
}
