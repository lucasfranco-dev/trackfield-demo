package com.trackfield.todolist.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trackfield.todolist.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = Store.TABLE_NAME)
@Table(name = Store.TABLE_NAME)
@Setter
@Getter
public class Store {
    public static final String TABLE_NAME = "stores";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_cpf", referencedColumnName = "cpf", nullable = false)
    @JsonBackReference("owner-stores")
    private User owner;

    @OneToMany(mappedBy = "worksAtStore")
    @JsonManagedReference("store-sellers")
    private List<User> sellers;

    @NotNull
    String storeName;

    @Embedded
    Location location;
}

