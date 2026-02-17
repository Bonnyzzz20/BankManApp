package com.example.bankmanapp.Controller;


import com.example.bankmanapp.Dto.MovimentiDto;
import com.example.bankmanapp.Service.MovimentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/Movimento")
public class MovimentiController {

    @Autowired
    MovimentiService movimentiService;


    @GetMapping(value = "/{id}")
    public MovimentiDto getMovimenti(@PathVariable int id) {
        MovimentiDto movimenti = movimentiService.trovaPerId(id);
        return movimenti;
    }


    @GetMapping(value = "/Movimenti")
    public List <MovimentiDto> getAllMovimenti(){
        return movimentiService.findAll();

    }

}
