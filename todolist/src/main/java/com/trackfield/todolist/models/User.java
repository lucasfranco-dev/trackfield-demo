package com.trackfield.todolist.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trackfield.todolist.dtos.UserDTO;
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
    private List<Tasks> tasks;

    private String getFirstName(String name) {
        return name.split(" ", 2)[0];

    }

    private String getLastName(String name) {
        String[] partes = name.split(" ", 2);
        return partes.length > 1 ? partes[1] : "";
    }


}
