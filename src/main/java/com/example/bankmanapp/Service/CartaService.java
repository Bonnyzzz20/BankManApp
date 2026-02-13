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
