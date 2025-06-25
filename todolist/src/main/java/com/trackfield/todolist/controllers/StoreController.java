package com.trackfield.todolist.controllers;

import com.trackfield.todolist.dtos.store.StoreDTO;
import com.trackfield.todolist.dtos.store.StoreResponseDTO;
import com.trackfield.todolist.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> getAll(){
        List<StoreResponseDTO> response = storeService.getAllStores();

        if(response.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StoreResponseDTO> createStore(@RequestBody StoreDTO storeDTO){
        StoreResponseDTO createdStoreResponse = storeService.createStore(storeDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStoreResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(createdStoreResponse);
    }
}
