package com.trackfield.todolist.services;

import com.trackfield.todolist.dtos.store.SimpleStoreResponseDTO;
import com.trackfield.todolist.dtos.user.OwnerResponseDTO;
import com.trackfield.todolist.dtos.user.SellerOrOwnerView;
import com.trackfield.todolist.exceptions.EntityNotFoundException;
import com.trackfield.todolist.dtos.user.UserDTO;
import com.trackfield.todolist.dtos.user.SellerResponseDTO;
import com.trackfield.todolist.models.Store;
import com.trackfield.todolist.models.user.User;
import com.trackfield.todolist.models.user.UserType;
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

    public SellerOrOwnerView findById(String cpf){
        User findedUser = userRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo CPF: " + cpf));

        SellerOrOwnerView response = sellerOrOwner(findedUser);
        return response;
    }

    public List<SellerOrOwnerView> findAll(){
        List<User> findedUsers = userRepository.findAll();

        return findedUsers.stream()
                .map(user -> sellerOrOwner(user)).toList();
    }

    public SellerOrOwnerView createNewUser(UserDTO data){
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
        User createdUser = userRepository.save(newUser);

        return sellerOrOwner(createdUser);
    }

    private SellerOrOwnerView sellerOrOwner(User user){
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
