package com.trackfield.todolist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trackfield.todolist.dtos.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = User.TABLE_NAME)
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class User {
    public static final String TABLE_NAME = "users";

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
