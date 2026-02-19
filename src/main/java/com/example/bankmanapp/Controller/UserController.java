package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/User")
public class UserController {

    @Autowired
    UserService userService;

    // Metodo che trova un solo utente tramite id
    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable int id) {
        UserDto user = userService.trovaPerId(id);
        return user;
    }

    // Metodo che restituisce tutti i record in db
    @GetMapping(value= "/Users")
    public List<UserDto> getAllUsers(){
        return userService.findAll();
    }

    // --- METODI AGGIUNTI ---

    // Metodo per la registrazione di un nuovo utente
    // Usa ResponseEntity per restituire lo status 201 Created
    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User nuovoUtente) {
        UserDto creato = userService.registraUtente(nuovoUtente);
        return new ResponseEntity<>(creato, HttpStatus.CREATED);
    }

    // Metodo per aggiornare un utente esistente
    // Riceve l'ID dall'URL e i dati aggiornati tramite UserDto nel body
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        try {
            UserDto aggiornato = userService.aggiornaUtente(id, userDto);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Metodo per eliminare un utente tramite ID
    // Restituisce 204 No Content se l'operazione va a buon fine
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.eliminaUtente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}////
