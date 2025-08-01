package com.trackfield.todolist.controllers;

import com.trackfield.todolist.services.TaskService;
import com.trackfield.todolist.dtos.task.TaskDTO;
import com.trackfield.todolist.dtos.task.TaskResponseDTO;
import com.trackfield.todolist.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity getTask(@PathVariable Long id){ // <<-- Retorna o DTO
        TaskResponseDTO findedTask = taskService.getTaskByIdResponse(id);

        return ResponseEntity.ok(findedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity toggleTaskStatus(@PathVariable Long id){
        boolean newStatus = taskService.toggleTaskStatus(id);
        if (newStatus) {
            return ResponseEntity.status(200).body("Tarefa alterada para CONCLUÍDA");
        }
        return ResponseEntity.status(200).body("Tarefa alterada para ATIVA");
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskDTO data){
        TaskResponseDTO createdTaskResponse = taskService.createTask(data);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTaskResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(createdTaskResponse);
    }

}
