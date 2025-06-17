package com.trackfield.todolist.Services;

import com.trackfield.todolist.Exceptions.EntityNotFoundException;
import com.trackfield.todolist.dtos.SimpleUserResponseDTO;
import com.trackfield.todolist.dtos.UserDTO;
import com.trackfield.todolist.dtos.UserResponseDTO;
import com.trackfield.todolist.dtos.UserResponseFindAllDTO;
import com.trackfield.todolist.models.User;
import com.trackfield.todolist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    public UserResponseDTO getUserByID(String cpf){
        User findedUser = userRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo CPF: " + cpf));

        UserResponseDTO response = new UserResponseDTO(findedUser.getCpf(),
                findedUser.getFirstName(),
                findedUser.getLastName(),
                findedUser.getEmail(),
                findedUser.getUserType(),
                taskService.findActiveTasksByCpf(findedUser.getCpf()));
        return response;
    }

    public List<UserResponseFindAllDTO> findAll(){
        List<User> findedUsers = userRepository.findAll();

        return findedUsers.stream()           // Converte a lista para um fluxo de dados.
                .map(user -> new UserResponseFindAllDTO( // Para cada 'user' no fluxo...
                        user.getCpf(),                   // ...cria um novo SimpleUserResponseDTO.
                        user.getFirstName(),
                        user.getLastName(),
                        taskService.findActiveTasksByCpf(user.getCpf()) //Mostra as tasks ATIVAS do usuário
                ))
                .collect(Collectors.toList()); // Coleta os DTOs criados em uma nova lista.
    }

    public User createNewUser(UserDTO data){
        User newUser = new User(data);
        return userRepository.save(newUser);
    }

    private void verifyIsNull(String string){
        if (string.isBlank() || string.isEmpty()){
            throw new RuntimeException("value reported is null");
        }
    }
}
