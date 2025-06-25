package com.trackfield.todolist.services;

import com.trackfield.todolist.dtos.store.NominatimAPI.NominatimResponseDTO;
import com.trackfield.todolist.dtos.store.StoreDTO;
import com.trackfield.todolist.dtos.store.StoreResponseDTO;
import com.trackfield.todolist.dtos.user.OwnerResponseDTO;
import com.trackfield.todolist.dtos.user.SimpleUserResponseDTO;
import com.trackfield.todolist.exceptions.AddressNotFoundException;
import com.trackfield.todolist.exceptions.EntityNotFoundException;
import com.trackfield.todolist.models.Location;
import com.trackfield.todolist.models.Store;
import com.trackfield.todolist.models.user.User;
import com.trackfield.todolist.repositories.StoreRepository;
import com.trackfield.todolist.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.trackfield.todolist.utils.UserUtils.isOwner;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public List<StoreResponseDTO> getAllStores(){
        List<Store> findedStores = storeRepository.findAll();


        return findedStores.stream()
                .map(store -> new StoreResponseDTO(store.getId(), store.getStoreName(), toSimpleUserResponseDTO(store.getOwner().getCpf()),
                        store.getLocation().getStreet(), store.getLocation().getCity(), store.getLocation().getState(),
                        store.getLocation().getPostalCode())).collect(Collectors.toList());
    }

    @Transactional
    public StoreResponseDTO createStore(StoreDTO data){
        String address = String.join(", ", data.street(), data.city(), data.state());

        Location location = new Location();

        location.setCity(data.city());
        location.setStreet(data.street());
        location.setState(data.state());
        location.setPostalCode(data.postalCode());

        Store newStore = new Store();

        newStore.setStoreName(data.storeName());

        User owner = userRepository.findById(data.cpfStoreOwner())
                .orElseThrow(() -> new EntityNotFoundException("Tentativa de definir dono recusada: " +
                        "Usuário não encontrado pelo CPF: " + data.cpfStoreOwner()));
        if (!isOwner(owner)){
            throw new IllegalArgumentException("Apenas usuários do tipo OWNER(Dono) podem criar lojas.");
        }

        newStore.setOwner(owner);


        newStore.setLocation(location);
        Store createdStore = storeRepository.save(newStore);

        return toStoreResponseDTO(createdStore);
    }

    private StoreResponseDTO toStoreResponseDTO(Store store){
        //VARIAVEIS AUXILIARES
        User owner = store.getOwner();
        Location location = store.getLocation();

        //CRIANDO O RESPONSE DE OWNER SIMPLIFICADO QUE VAI SER RETORNADO DENTRO DO STORE RESPONSE

        SimpleUserResponseDTO ownerResponse = new SimpleUserResponseDTO(owner.getCpf(), owner.getFirstName(),
                owner.getLastName(), owner.getUserType());


        StoreResponseDTO response = new StoreResponseDTO(store.getId(), store.getStoreName(), ownerResponse,
                location.getStreet(), location.getState(), location.getState(), location.getPostalCode());

        return response;
    }

    private SimpleUserResponseDTO toSimpleUserResponseDTO(String cpf){
        User findedUser = userRepository.findById(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo ID: "+ cpf));

        return new SimpleUserResponseDTO(findedUser.getCpf(), findedUser.getFirstName(), findedUser.getLastName(),
                findedUser.getUserType());
    }
}
