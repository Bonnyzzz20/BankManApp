package com.example.bankmanapp.Service;

import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.TipoCarta;
import com.example.bankmanapp.Repository.CartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaService {

    @Autowired
    private CartaRepository cartaRepository;


    public List<CartaDto> findAll() {
        return cartaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CartaDto findById(int id) {
        return cartaRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Carta non trovata con ID: " + id));
    }

    @Transactional
    public CartaDto create(Carta carta) {
        // qua nel caso va logica
        Carta salvata = cartaRepository.save(carta);
        return convertToDto(salvata);
    }

    @Transactional
    public CartaDto update(int id, Carta dettagli) {
        Carta esistente = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile aggiornare una carta inesistente"));


        esistente.setTitolare(dettagli.getTitolare());
        esistente.setMassimaleMensile(dettagli.getMassimaleMensile());
        esistente.setAttiva(dettagli.isAttiva());
        esistente.setFido(dettagli.getFido());


        return convertToDto(cartaRepository.save(esistente));
    }

    public void delete(int id) {
        if (!cartaRepository.existsById(id)) {
            throw new RuntimeException("ID carta non valido per l'eliminazione");
        }
        cartaRepository.deleteById(id);
    }





    public CartaDto convertToDto(Carta carta) {

        return new CartaDto(
                carta.getId(),
                carta.getNumeroCarta(),
                carta.getTitolare(),
                carta.getDataScadenza(),
                carta.getCvv(),
                carta.getPin(),
                carta.getTipo(),
                carta.getFido(),
                carta.getMassimaleMensile(),
                carta.isAttiva()
        );
    }
}
