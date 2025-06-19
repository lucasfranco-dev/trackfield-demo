package com.trackfield.todolist.services;

import com.trackfield.todolist.dtos.task.SimpleTaskResponseDTO;
import com.trackfield.todolist.dtos.task.TaskDTO;
import com.trackfield.todolist.dtos.task.TaskResponseDTO;
import com.trackfield.todolist.dtos.user.SimpleUserResponseDTO;
import com.trackfield.todolist.exceptions.EntityNotFoundException;
import com.trackfield.todolist.models.Task;
import com.trackfield.todolist.models.User;
import com.trackfield.todolist.repositories.TaskRepository;
import com.trackfield.todolist.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public Task getTaskById(Long id){
        return taskRepository.getReferenceById(id);
    }

    @Transactional
    public boolean toggleTaskStatus(Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o id: " + taskId));
        if (task.getFinished()) {
            task.setFinished(false);
            return false;
        }
        task.setFinished(true);
        return true;
    }

    public List<SimpleTaskResponseDTO> findActiveTasksByCpf(String userCpf){
        List<Task> activeTasks = taskRepository.findByUserCpfAndFinished(userCpf, false);

        return activeTasks.stream().map(tasks -> new SimpleTaskResponseDTO(
                tasks.getId(),
                tasks.getTitle(),
                tasks.getDescription()
        )).collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskByIdResponse(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o id: " + id));
        return toResponseDTO(task);
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        SimpleUserResponseDTO userDTO = new SimpleUserResponseDTO(
                task.getUser().getCpf(),
                task.getUser().getFirstName(),
                task.getUser().getLastName()
        );
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), userDTO);
    }

    public Task createTask(TaskDTO data){
        User user = userRepository.findById(data.userCpf())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + data.userCpf()));

        Task newTask = new Task();
        newTask.setTitle(data.title());
        newTask.setDescription(data.description());
        newTask.setUser(user);
        return taskRepository.save(newTask);
    }


}
