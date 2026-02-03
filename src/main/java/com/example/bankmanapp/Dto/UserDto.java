package com.example.bankmanapp.Dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record UserDto(
        Long id,


        @NotBlank(message = "Il nome non può essere vuoto")
        String nome,


        @NotBlank( message= "Il cognome non può essere vuoto")
        String cognome,



        @Email(message = "Formato email non valido")
        @NotBlank(message = "L'email è obbligatoria")
        String email,

        //Serve a controllare 6 lettere iniziali, 2 numeri anno, 1 lettera mese, 2 numeri giorno e sesso
        //1 lettera comune, 3 numeri progressivo, 1 lettera carattere di controllo
        @Pattern(
                //definisce il criterio che il testo deve soddisfare
                regexp  =  "^[A-Za-z0-9]{16}$",
                message  =  "Il codice fiscale deve contenere 16 caratteri alfanumerici"
        )
        @NotBlank(message = "Il codice fiscale è obbligatorio")
        String codiceFiscale

) {
}

