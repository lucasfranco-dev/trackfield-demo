package com.trackfield.todolist.controllers;

import com.trackfield.todolist.Services.TaskService;
import com.trackfield.todolist.dtos.TaskDTO;
import com.trackfield.todolist.dtos.TaskResponseDTO;
import com.trackfield.todolist.models.Tasks;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id){ // <<-- Retorna o DTO
        TaskResponseDTO findedTask = taskService.getTaskByIdResponse(id);

        return ResponseEntity.ok(findedTask);
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskDTO data){
        Tasks task = taskService.createTask(data);

        TaskResponseDTO response = taskService.getTaskByIdResponse(task.getId());


        return ResponseEntity.ok(response);
    }
}
