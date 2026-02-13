package com.example.bankmanapp.Controller;

import com.example.bankmanapp.Dto.CartaDto;
import com.example.bankmanapp.Service.CartaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carte")
public class CartaController {

    private final CartaService cartaService;

    public CartaController(CartaService cartaService) {
        this.cartaService = cartaService;
    }

    @GetMapping
    public List<CartaDto> getAll() {
        return cartaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> getById(@PathVariable int id) {
        CartaDto dto = cartaService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}



