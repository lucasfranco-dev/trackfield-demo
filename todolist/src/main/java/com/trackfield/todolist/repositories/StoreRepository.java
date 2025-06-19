package com.trackfield.todolist.repositories;

import com.trackfield.todolist.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, String> {
    public List<Store> findByOwnerCpf(String ownerCpf);

}
