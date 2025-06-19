package com.trackfield.todolist.services;

import com.trackfield.todolist.dtos.store.SimpleStoreResponseDTO;
import com.trackfield.todolist.dtos.user.OwnerResponseDTO;
import com.trackfield.todolist.dtos.user.SellerOrOwnerView;
import com.trackfield.todolist.exceptions.EntityNotFoundException;
import com.trackfield.todolist.dtos.user.UserDTO;
import com.trackfield.todolist.dtos.user.SellerResponseDTO;
import com.trackfield.todolist.models.Store;
import com.trackfield.todolist.models.User;
import com.trackfield.todolist.models.UserType;
import com.trackfield.todolist.repositories.StoreRepository;
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
    private final StoreRepository storeRepository;

    public SellerResponseDTO findById(String cpf){
        User findedUser = userRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo CPF: " + cpf));

        SellerResponseDTO response = new SellerResponseDTO(findedUser.getCpf(),
                findedUser.getFirstName(),
                findedUser.getLastName(),
                findedUser.getEmail(),
                findedUser.getUserType(),
                taskService.findActiveTasksByCpf(findedUser.getCpf()));
        return response;
    }

    public List<SellerOrOwnerView> findAll(){
        List<User> findedUsers = userRepository.findAll();

        return findedUsers.stream()
                .map(user -> teste(user)).toList();
    }

    public User createNewUser(UserDTO data){
        User newUser = new User(data);

        if (data.userType() == UserType.SELLER){
            if (data.worksAtStoreId() == null){
                throw new IllegalArgumentException("Vendedores(SELLER) precisam trabalhar em alguma loja");
            }

            newUser.setWorksAtStore(storeRepository.findById(data.worksAtStoreId())
                    .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada pelo ID: "
                            + data.worksAtStoreId())));
        }

        else if(data.userType() == UserType.OWNER){
            if (data.worksAtStoreId() != null){
                throw new IllegalArgumentException("Donos(OWNER) não podem trabalhar em nenhuma loja");
            }
        }

        return userRepository.save(newUser);
    }

    private SellerOrOwnerView teste(User user){
        if (user.getUserType() == UserType.SELLER){
            return new SellerResponseDTO(
                    user.getCpf(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUserType(),
                    taskService.findActiveTasksByCpf(user.getCpf()));
        }
        return new OwnerResponseDTO(
                user.getCpf(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserType(),
                findStoreByOwnerCpf(user.getCpf()));
    }

    private List<SimpleStoreResponseDTO> findStoreByOwnerCpf(String cpf){
        List<Store> findedStores = storeRepository.findByOwnerCpf(cpf);

        return findedStores.stream()
                .map(store -> new SimpleStoreResponseDTO(
                        store.getId(),
                        store.getStoreName()
                )).collect(Collectors.toList());
    }
}
