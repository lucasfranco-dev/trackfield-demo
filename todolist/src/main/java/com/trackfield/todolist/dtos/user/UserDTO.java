package com.trackfield.todolist.dtos.user;

import com.trackfield.todolist.models.user.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "O CPF não pode estar em branco.")
        @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "O nome não pode estar em branco.")
        String name,

        @NotBlank(message = "O email não pode estar em branco.")
        String email,

        @NotBlank(message = "A senha não pode estar em branco.")
        String password,

        @NotNull(message = "O tipo de usuário não pode ser nulo")
        UserType userType,

        String worksAtStoreId)
{
}
