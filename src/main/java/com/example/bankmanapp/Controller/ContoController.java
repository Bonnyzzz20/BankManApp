package com.example.bankmanapp.Controller;


import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Service.ContoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/Conto")
public class ContoController {
    @Autowired
    ContoService contoService;



    @GetMapping(value = "/{id}")
    public ContoDto getConto(@PathVariable int id){
        ContoDto conto = contoService.trovaPerId(id);
        return conto;
    }

@GetMapping (value = "/Conti")
public List<ContoDto> getAllConti(){
        return contoService.findAll();
}


}
