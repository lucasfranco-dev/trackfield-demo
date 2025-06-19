package com.trackfield.todolist.controllers;

import com.trackfield.todolist.services.TaskService;
import com.trackfield.todolist.dtos.task.TaskDTO;
import com.trackfield.todolist.dtos.task.TaskResponseDTO;
import com.trackfield.todolist.models.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

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
    public ResponseEntity createTask(@RequestBody TaskDTO data){
        Task task = taskService.createTask(data);

        TaskResponseDTO response = taskService.getTaskByIdResponse(task.getId());


        return ResponseEntity.ok(response);
    }

}
