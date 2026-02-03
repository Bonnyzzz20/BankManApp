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
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
    }


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
                user.getEmail()
        );
    }
}
