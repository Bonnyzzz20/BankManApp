package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Salva un nuovo utente e restituisce il DTO
    public UserDto registraUtente(User nuovoUtente) {
        User utenteSalvato = userRepository.save(nuovoUtente);
        return convertToDto(utenteSalvato);
    }


    //Trova un utente per ID.
    // Metodo robusto: valida l'input, previene SQL Injection tramite JPA
    // e gestisce l'assenza del dato con un'eccezione esplicita.

    public UserDto trovaPerId(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");

        }
        // Utilizzo di Optional per una gestione sicura
        // in caso un dato potrebbe non esistere
        //Mentre findAll() restituisce sempre una lista (che al massimo è vuota),
        // i metodi che cercano un singolo elemento (come findById) in Spring Data JPA restituiscono
        // un Optional<User>.

        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));


    }





     //.stream() trasforma la lista in un flusso ordinato di dati
     //.map(this::convertToDto) per ogni record viene applicata la trasformaione
     //.collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    // Conversione Model  DTO
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getNome(),
                user.getCognome(),
                user.getEmail(),
                user.getCodiceFiscale()
        );
    }
}
