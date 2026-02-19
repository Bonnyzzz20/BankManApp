package com.example.bankmanapp.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserDto(

        int id,
        @NotBlank(message = "Il nome non può essere vuoto")
        String nome,

        @NotBlank(message = "Il cognome non può essere vuoto")
        String cognome,

        LocalDate dataDiNascita,
        int cellulare,
        String citta,
        String regione,
        String provincia,
        String nazione,
        int cap,
        String indirizzo,

        @NotBlank(message = "Il codice fiscale è obbligatorio")
        String codiceFiscale,

        @Email(message = "Formato email non valido")
        @NotBlank(message = "L'email è obbligatoria")
        String email,

        @NotBlank(message = "La password è obbligatoria")
        String password
) {
}
