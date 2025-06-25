package com.trackfield.todolist.models.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trackfield.todolist.dtos.user.UserDTO;
import com.trackfield.todolist.models.Store;
import com.trackfield.todolist.models.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = User.TABLE_NAME)
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
@Getter
@Setter
public class User {
    public static final String TABLE_NAME = "users";

    public User(UserDTO userDTO){
        this.cpf = userDTO.cpf();
        this.firstName = getFirstName(userDTO.name());
        this.lastName = getLastName(userDTO.name());
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.userType = userDTO.userType();
    }

    @Id
    @NotBlank
    @Column(unique = true)
    private String cpf;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("owner-stores")
    private List<Store> ownedStores;

    @ManyToOne
    @JoinColumn(name = "works_at_store_id")
    @JsonBackReference("store-sellers")
    private Store worksAtStore;

    private String getFirstName(String name) {
        return name.split(" ", 2)[0];

    }



    private String getLastName(String name) {
        String[] partes = name.split(" ", 2);
        return partes.length > 1 ? partes[1] : "";
    }


}
