package com.example.bankmanapp.Service;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Dto.UserDto;
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


    public MovimentiDto salvaNuovoMovimento(Movimenti nuovoMovimento) {
        Movimenti movimentoSalvato = movimentoRepository.save(nuovoMovimento);
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



    private MovimentiDto convertToDto(Movimenti movimenti) {
        return new MovimentiDto(

                movimenti.getId(),
                movimenti.getIdConto(),
                movimenti.getImporto(),
                movimenti.getTipo(),
                movimenti.getData()


        );
    }


}
