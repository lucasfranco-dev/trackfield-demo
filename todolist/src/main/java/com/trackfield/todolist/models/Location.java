package com.trackfield.todolist.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable //Torna esta classe reutilizável e embutível em outras entidades, seram gerar novas tabelas separadas.
@Getter
@Setter
public class Location {

    private String street;
    private String city;
    private String state;
    private String postalCode;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;
}
