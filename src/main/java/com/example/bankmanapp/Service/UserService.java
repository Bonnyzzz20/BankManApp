package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Trova un utente per ID.
    // Metodo robusto: valida l'input, previene SQL Injection tramite JPA
    // e gestisce l'assenza del dato con un'eccezione esplicita.
    public UserDto trovaPerId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");
        }

        // Utilizzo di Optional per una gestione sicura
        // in caso un dato potrebbe non esistere
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
    }

    // .stream() trasforma la lista in un flusso ordinato di dati
    // .map(this::convertToDto) per ogni record viene applicata la trasformaione
    // .collect(Collectors.toList()) prende tutti i record DTO r li inserisce in una lista finale
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // --- METODI AGGIUNTI (UPDATE E DELETE) ---

    @Transactional
    public UserDto aggiornaUtente(int id, UserDto dto) {
        // Recuperiamo l'Entity dal database
        User esistente = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile aggiornare: Utente non trovato con ID: " + id));

        // Aggiorniamo l'Entity usando i dati dal Record DTO (usando i tuoi setter con validazione)
        esistente.setNome(dto.nome());
        esistente.setCognome(dto.cognome());
        esistente.setEmail(dto.email());
        esistente.setCodiceFiscale(dto.codiceFiscale());

        // Salviamo le modifiche
        User salvato = userRepository.save(esistente);

        // Restituiamo il DTO aggiornato
        return convertToDto(salvato);
    }

    public void eliminaUtente(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Errore: Utente con ID " + id + " non esiste.");
        }
        userRepository.deleteById(id);
    }

    // Conversione Model -> DTO (Completo con i 14 campi richiesti)
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
                user.getEmail(),
                null, // La password NON va mai inviata nel DTO (sicurezza)
                user.getCodiceFiscale()
        );
    }


}
//