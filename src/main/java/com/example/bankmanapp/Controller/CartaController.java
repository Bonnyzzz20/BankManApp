package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Service.CartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Carta")
public class CartaController {

    @Autowired
    private CartaService cartaService;

    // Metodo per trovare tutte le carte
    @GetMapping
    public List<CartaDto> getAll() {
        return cartaService.findAll();
    }

    // Metodo per trovare una carta tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(cartaService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per creare una nuova carta
    @PostMapping
    public ResponseEntity<CartaDto> create(@RequestBody Carta nuovaCarta) {
        CartaDto creata = cartaService.creaCarta(nuovaCarta);
        return new ResponseEntity<>(creata, HttpStatus.CREATED);
    }

    // Metodo per aggiornare una carta esistente tramite DTO
    @PutMapping("/{id}")
    public ResponseEntity<CartaDto> update(@PathVariable int id, @RequestBody CartaDto cartaDto) {
        try {
            CartaDto aggiornata = cartaService.update(id, cartaDto);
            return ResponseEntity.ok(aggiornata);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare una carta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            cartaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}////
