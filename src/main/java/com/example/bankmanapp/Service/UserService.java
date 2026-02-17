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


    public UserDto update(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));

        user.setNome(userDto.nome());
        user.setCognome(userDto.cognome());
        user.setDataDiNascita(userDto.dataDiNascita());
        user.setCellulare(userDto.cellulare());
        user.setCitta(userDto.citta());
        user.setRegione(userDto.regione());
        user.setProvincia(userDto.provincia());
        user.setNazione(userDto.nazione());
        user.setCap(userDto.cap());
        user.setIndirizzo(userDto.indirizzo());
        user.setCodiceFiscale(userDto.codiceFiscale());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        return convertToDto(userRepository.save(user));
    }


    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: utente inesistente.");
        }
        userRepository.deleteById(id);
    }


    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getNome(),
                user.getCognome(),
                user.getDataDiNascita(),
                user.getCellulare(),
                user.getCitta(),
                user.getRegione(),
                user.getProvincia(),
                user.getNazione(),
                user.getCap(),
                user.getIndirizzo(),
                //non vogliamo mostrare il codice fiscale
                null,
                //non vogliamo mostrare l'email
                null,
                //non vogliamo mostrare la password
                null
        );
    }
}