package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Repository.CartaRepository;
import com.example.bankmanapp.Service.CartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carta")
public class CartaController {


    @Autowired
    private CartaRepository repository;













//    @GetMapping
//    public List<CartaDto> getAll() {
//        return cartaService.findAll();
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CartaDto> getById(@PathVariable int id) {
//        CartaDto dto = cartaService.findById(id);
//        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
//    }
}



