package com.example.bankmanapp.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UserDto(
        int id,


        @NotBlank(message = "Il nome non può essere vuoto")
        String nome,


        @NotBlank( message= "Il cognome non può essere vuoto")
        String cognome,


        @Email(message = "Formato email non valido")
        @NotBlank(message = "L'email è obbligatoria")
        String email,


        @NotBlank(message = "Il codice fiscale è obbligatorio")
        String codiceFiscale

) {
}

