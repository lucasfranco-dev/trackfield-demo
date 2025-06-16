package com.trackfield.todolist.Services;

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

    public User getUserByID(String cpf){
        verifyIsNull(cpf);

        User findUser = userRepository.getReferenceById(cpf);

        return findUser;
    }

    public List<UserResponseFindAllDTO> findAll(){
        List<User> findedUsers = userRepository.findAll();

        return findedUsers.stream()           // Converte a lista para um fluxo de dados.
                .map(user -> new UserResponseFindAllDTO( // Para cada 'user' no fluxo...
                        user.getCpf(),                   // ...cria um novo SimpleUserResponseDTO.
                        user.getFirstName(),
                        user.getLastName(),
                        taskService.simpleFindAllByUserCpf(user.getCpf())
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
