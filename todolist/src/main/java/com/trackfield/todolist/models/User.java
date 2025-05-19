package com.trackfield.todolist.models;

import com.trackfield.todolist.dtos.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = User.TABLE_NAME)
@Table(name = User.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    public static final String TABLE_NAME = "user";

    public User(UserDTO userDTO){
        this.firstName = getFirstName(userDTO.name());
        this.lastName = getLastName(userDTO.name());
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.userType = userDTO.userType();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String id;

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

    private String getFirstName(String nomeCompleto) {
        String[] partes = nomeCompleto.trim().split(" ");
        return partes.length > 0 ? partes[0] : "";
    }

    private String getLastName(String nomeCompleto) {
        String[] partes = nomeCompleto.trim().split(" ");
        return partes.length > 1 ? partes[partes.length - 1] : "";
    }
}
