package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;
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

    public CartaDto creaCarta(Carta nuovoCarta) {
        Carta salvato = cartaRepository.save(nuovoCarta);
        return toDto(salvato);
    }

    public List<CartaDto> findAll() {
        return cartaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CartaDto findById(int id) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta non trovata con ID: " + id));
        return toDto(carta);
    }

    // Aggiunta @Transactional per assicurare il salvataggio dei setter
    @Transactional
    public CartaDto update(int id, CartaDto cartaDto) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta non trovata con ID: " + id));

        carta.setNumeroCarta(cartaDto.numeroCarta());
        carta.setTitolare(cartaDto.titolare());
        carta.setDataScadenza(cartaDto.dataScadenza());
        carta.setCvv(cartaDto.cvv());
        carta.setPin(cartaDto.pin());
        carta.setTipo(cartaDto.tipo());
        carta.setFido(cartaDto.fido());
        carta.setMassimaleMensile(cartaDto.massimaleMensile());
        carta.setAttiva(cartaDto.attiva());

        return toDto(cartaRepository.save(carta));
    }

    public void delete(int id) {
        if (!cartaRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: carta inesistente.");
        }
        cartaRepository.deleteById(id);
    }

    private CartaDto toDto(Carta carta) {
        return new CartaDto(
                carta.getId(),
                //numero carta che non vogliamo mostrare
                null,
                carta.getTitolare(),
                carta.getDataScadenza(),
                //cvv che non vogliamo mostrare
                null,
                //pin che non vogliamo mostrare
                null,
                carta.getTipo(),
                carta.getFido(),
                carta.getMassimaleMensile(),
                carta.isAttiva()
        );
    }
}
