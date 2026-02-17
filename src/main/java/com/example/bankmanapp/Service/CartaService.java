package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Repository.CartaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaService {

    private final CartaRepository cartaRepository;

    public CartaService(CartaRepository cartaRepository) {
        this.cartaRepository = cartaRepository;
    }


    //CREA NUOVA CARTA
    public CartaDto creaCarta() {
        Carta carta = new Carta();
        carta.setAttiva(true);
        carta.setFido(0.0);
        carta.setMassimaleMensile(0.0);

        Carta saved = cartaRepository.save(carta);
        return toDto(saved);
    }


    //SALVA CARTA
    public CartaDto salvaCarta(CartaDto cartaDto) {
        Carta carta = new Carta();
        carta.setNumeroCarta(cartaDto.numeroCarta());
        carta.setTitolare(cartaDto.titolare());
        carta.setDataScadenza(cartaDto.dataScadenza());
        carta.setCvv(cartaDto.cvv());
        carta.setPin(cartaDto.pin());
        carta.setTipo(cartaDto.tipo());
        carta.setFido(cartaDto.fido());
        carta.setMassimaleMensile(cartaDto.massimaleMensile());
        carta.setAttiva(cartaDto.attiva());

        Carta saved = cartaRepository.save(carta);
        return toDto(saved);
    }


    public List<CartaDto> findAll() {
        return cartaRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public CartaDto findById(int id) {
        return cartaRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }


    //INSERT
    public CartaDto insert(CartaDto cartaDto) {
        return salvaCarta(cartaDto);
    }


    //UPDATE
    public CartaDto update(int id, CartaDto cartaDto) {
        return cartaRepository.findById(id)
                .map(carta -> {
                    carta.setNumeroCarta(cartaDto.numeroCarta());
                    carta.setTitolare(cartaDto.titolare());
                    carta.setDataScadenza(cartaDto.dataScadenza());
                    carta.setCvv(cartaDto.cvv());
                    carta.setPin(cartaDto.pin());
                    carta.setTipo(cartaDto.tipo());
                    carta.setFido(cartaDto.fido());
                    carta.setMassimaleMensile(cartaDto.massimaleMensile());
                    carta.setAttiva(cartaDto.attiva());

                    Carta updated = cartaRepository.save(carta);
                    return toDto(updated);
                })
                .orElse(null);
    }


    //DELETE
    public boolean delete(int id) {
        if (cartaRepository.existsById(id)) {
            cartaRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private CartaDto toDto(Carta carta) {
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
