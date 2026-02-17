package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Repository.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContoService {
    @Autowired
    private ContoRepository contoRepository;

    public ContoDto creaConto(Conto nuovoConto) {

        Conto salvato = contoRepository.save(nuovoConto);
        return convertToDto(salvato);
    }


    public ContoDto trovaPerId(int id) {

        if ( id <= 0) {
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");
        }

        Conto conto = contoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conto non trovato con ID: " + id));
        return convertToDto(conto);
    }


    //  Trova tutti i conti di un singolo cliente
    public List<ContoDto>findAll() {
        return contoRepository.findAll().stream()
               // .filter(c -> c.getIdUtente().equals(idUtente)) // Filtra per ID Utente
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    //  Trasforma il Model in DTO
    private ContoDto convertToDto(Conto conto) {
        return new ContoDto(
                conto.getId(),
                conto.getIdUtente().getId(),
                conto.getIban(),
                conto.getSaldo()
        );
    }
}