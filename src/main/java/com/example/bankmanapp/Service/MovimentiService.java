package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Model.Movimenti;
import com.example.bankmanapp.Repository.MovimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MovimentiService {
    @Autowired
    private MovimentoRepository movimentoRepository;


    public MovimentiDto salvaNuovoMovimento(Movimenti nuovoMovimenti) {
        Movimenti movimentoSalvato = movimentoRepository.save(nuovoMovimenti);
        return convertToDto(movimentoSalvato);
    }


    public MovimentiDto trovaPerId(int id) {
        if ( id <= 0) {
            throw new IllegalArgumentException("ID non valido: deve essere un valore numerico positivo.");
        }
        return movimentoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Movimento non trovato con ID: " + id));
    }


    public List<MovimentiDto> findAll() {
        return movimentoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // INSERT
    public MovimentiDto insert(Movimenti nuovoMovimenti) {
        Movimenti movimentiSalvato = movimentoRepository.save(nuovoMovimenti);
        return convertToDto(movimentiSalvato);
    }


    // UPDATE
    public MovimentiDto update(int id, Movimenti movimentiAggiornato) {
        Movimenti movimentiEsistente = movimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimento non trovato con ID: " + id));

        //Aggiorna solo i campi modificabili
        movimentiEsistente.setConto(movimentiAggiornato.getConto());
        movimentiEsistente.setCarta(movimentiAggiornato.getCarta());
        movimentiEsistente.setImporto(movimentiAggiornato.getImporto());
        movimentiEsistente.setTipo(movimentiAggiornato.getTipo());

        Movimenti movimentiSalvato = movimentoRepository.save(movimentiEsistente);
        return convertToDto(movimentiSalvato);
    }


    // DELETE
    public void delete(int id) {
        if (!movimentoRepository.existsById(id)) {
            throw new RuntimeException("Movimento non trovato con ID: " + id);
        }
        movimentoRepository.deleteById(id);
    }


    private MovimentiDto convertToDto(Movimenti m) {
        return new MovimentiDto(
                m.getId(),
                m.getConto().getId(), // Devi chiamare getId() sull'oggetto Conto
                (m.getCarta() != null) ? m.getCarta().getId() : 0, // Gestisci il null per la carta
                m.getImporto(),
                m.getTipo(),
                m.getData()
        );
    }
}