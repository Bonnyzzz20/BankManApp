package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Model.Conto;
import com.example.bankmanapp.Service.ContoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Conto")
public class ContoController {

    @Autowired
    private ContoService contoService;

    // Metodo per creare un nuovo conto (POST)
    @PostMapping
    public ResponseEntity<ContoDto> createConto(@RequestBody Conto nuovoConto) {
        ContoDto creato = contoService.creaConto(nuovoConto);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    // Metodo per trovare un conto tramite ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ContoDto> getConto(@PathVariable int id) {
        try {
            ContoDto conto = contoService.trovaPerId(id);
            return ResponseEntity.ok(conto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per restituire tutti i conti (GET)
    @GetMapping
    public List<ContoDto> getAllConti() {
        return contoService.findAll();
    }

    // Metodo per aggiornare un conto tramite DTO (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ContoDto> updateConto(@PathVariable int id, @RequestBody ContoDto contoDto) {
        try {
            ContoDto aggiornato = contoService.aggiornaConto(id, contoDto);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare un conto (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConto(@PathVariable int id) {
        try {
            contoService.eliminaConto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
