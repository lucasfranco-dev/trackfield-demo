package com.trackfield.todolist.services;

import com.trackfield.todolist.dtos.store.NominatimAPI.NominatimResponseDTO;
import com.trackfield.todolist.dtos.store.StoreDTO;
import com.trackfield.todolist.dtos.store.StoreResponseDTO;
import com.trackfield.todolist.exceptions.AddressNotFoundException;
import com.trackfield.todolist.exceptions.EntityNotFoundException;
import com.trackfield.todolist.models.Location;
import com.trackfield.todolist.models.Store;
import com.trackfield.todolist.repositories.StoreRepository;
import com.trackfield.todolist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final UserService userService;

    private final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";

    public List<StoreResponseDTO> getAllStores(){
        List<Store> findedStores = storeRepository.findAll();
        return findedStores.stream()
                .map(store -> new StoreResponseDTO(store.getId(), store.getStoreName(), userService.findById(store.getOwner().getCpf()),
                        store.getLocation().getStreet(), store.getLocation().getCity(), store.getLocation().getState(),
                        store.getLocation().getPostalCode())).collect(Collectors.toList());
    }

    public Store createStore(StoreDTO data){
        String address = String.join(", ", data.street(), data.city(), data.state());

        Location location = geocodeAddress(address);

        location.setCity(data.city());
        location.setStreet(data.street());
        location.setState(data.state());
        location.setPostalCode(data.postalCode());

        Store newStore = new Store();

        newStore.setStoreName(data.storeName());
        newStore.setOwner(userRepository.findById(data.cpfStoreOwner())
                .orElseThrow(() -> new EntityNotFoundException("Tentativa de definir dono recusada: " +
                        "Usuário não encontrado pelo CPF: " + data.cpfStoreOwner())));
        newStore.setLocation(location);
        return storeRepository.save(newStore);
    }

    private Location geocodeAddress(String address){
        String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_API_URL)
                .queryParam("q", address)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .toUriString();

        NominatimResponseDTO[] response = restTemplate.getForObject(url, NominatimResponseDTO[].class);

        Location location = new Location();

        if (response != null && response.length > 0) {
            location.setLatitude(BigDecimal.valueOf(Double.parseDouble(response[0].getLon())));
            location.setLongitude(BigDecimal.valueOf(Double.parseDouble(response[0].getLon())));
        }
        else {
            throw new AddressNotFoundException("Não foi possível encontrar as coordenadas do endereço: "
            + address);
        }

        return location;
    }

    public Store verifyStoreExists(String storeId){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Não foi possível encontrar nenhuma loja com o id: "
                        +storeId));

        return store;
    }
}
