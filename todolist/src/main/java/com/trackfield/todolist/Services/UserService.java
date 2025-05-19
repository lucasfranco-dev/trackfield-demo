package com.trackfield.todolist.Services;

import com.trackfield.todolist.dtos.UserDTO;
import com.trackfield.todolist.models.User;
import com.trackfield.todolist.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByID(String UUID){
        verifyIsNull(UUID);
        User findUser = userRepository.getReferenceById(UUID);

        return findUser;
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
