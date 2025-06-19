package com.trackfield.todolist.dtos.store.NominatimAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NominatimResponseDTO {
    private String lat;
    private String lon;
    private String display_name;
}
