package com.trackfield.todolist.Services;

import com.trackfield.todolist.dtos.*;
import com.trackfield.todolist.models.Tasks;
import com.trackfield.todolist.models.User;
import com.trackfield.todolist.repositories.TaskRepository;
import com.trackfield.todolist.repositories.UserRepository;
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

    public List<SimpleTaskResponseDTO> simpleFindAllByUserCpf(String cpf){
        List<Tasks> findedTasks = taskRepository.findByUserCpf(cpf);

        return findedTasks.stream()           // Converte a lista para um fluxo de dados.
                .map(tasks -> new SimpleTaskResponseDTO( // Para cada 'user' no fluxo...
                        tasks.getId(),                   // ...cria um novo SimpleUserResponseDTO.
                        tasks.getTitle(),
                        tasks.getDescription()
                ))
                .collect(Collectors.toList()); // Coleta os DTOs criados em uma nova lista.
    }

    public TaskResponseDTO getTaskByIdResponse(Long id){
        Tasks task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o id: " + id));
        return toResponseDTO(task);
    }

    // Método conversor corrigido
    private TaskResponseDTO toResponseDTO(Tasks task) {
        // Use o novo DTO simplificado aqui
        SimpleUserResponseDTO userDTO = new SimpleUserResponseDTO(
                task.getUser().getCpf(),
                task.getUser().getFirstName(),
                task.getUser().getLastName()
        );
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), userDTO);
    }

    public Tasks createTask(TaskDTO data){
        User user = userRepository.findById(data.userCpf())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + data.userCpf()));

        Tasks newTask = new Tasks();
        newTask.setTitle(data.title());
        newTask.setDescription(data.description());
        newTask.setUser(user);
        return taskRepository.save(newTask);
    }


}
