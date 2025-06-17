package com.trackfield.todolist.Services;

import com.trackfield.todolist.Exceptions.EntityNotFoundException;
import com.trackfield.todolist.dtos.*;
import com.trackfield.todolist.models.Tasks;
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


    public Tasks getTaskById(Long id){
        return taskRepository.getReferenceById(id);
    }

    @Transactional
    public boolean toggleTaskStatus(Long taskId){
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o id: " + taskId));
        if (task.getFinished()) {
            task.setFinished(false);
            return false;
        }
        task.setFinished(true);
        return true;
    }

    public List<SimpleTaskResponseDTO> findActiveTasksByCpf(String userCpf){
        List<Tasks> activeTasks = taskRepository.findByUserCpfAndFinished(userCpf, false);

        return activeTasks.stream().map(tasks -> new SimpleTaskResponseDTO(
                tasks.getId(),
                tasks.getTitle(),
                tasks.getDescription()
        )).collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskByIdResponse(Long id){
        Tasks task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o id: " + id));
        return toResponseDTO(task);
    }

    private TaskResponseDTO toResponseDTO(Tasks task) {
        SimpleUserResponseDTO userDTO = new SimpleUserResponseDTO(
                task.getUser().getCpf(),
                task.getUser().getFirstName(),
                task.getUser().getLastName()
        );
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), userDTO);
    }

    public Tasks createTask(TaskDTO data){
        User user = userRepository.findById(data.userCpf())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + data.userCpf()));

        Tasks newTask = new Tasks();
        newTask.setTitle(data.title());
        newTask.setDescription(data.description());
        newTask.setUser(user);
        return taskRepository.save(newTask);
    }


}
