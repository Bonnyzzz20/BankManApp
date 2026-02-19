package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Model.Movimenti;
import com.example.bankmanapp.Service.MovimentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Movimenti")
public class MovimentiController {

    @Autowired
    private MovimentiService movimentiService;

    // Endpoint per ottenere tutti i movimenti
    @GetMapping
    public List<MovimentiDto> getAll() {
        return movimentiService.findAll();
    }

    // Endpoint per trovare un movimento specifico tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<MovimentiDto> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(movimentiService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint per l'inserimento di un nuovo movimento (Metodo insert del Service)
    @PostMapping
    public ResponseEntity<MovimentiDto> create(@RequestBody Movimenti nuovoMovimento) {
        MovimentiDto creato = movimentiService.creaMovimento(nuovoMovimento);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    // Endpoint per l'aggiornamento di un movimento esistente
    @PutMapping("/{id}")
    public ResponseEntity<MovimentiDto> update(@PathVariable int id, @RequestBody Movimenti movimentoAggiornato) {
        try {
            MovimentiDto aggiornato = movimentiService.update(id, movimentoAggiornato);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint per l'eliminazione di un movimento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            movimentiService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }////
}
