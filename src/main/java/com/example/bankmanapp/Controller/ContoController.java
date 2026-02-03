package com.example.bankmanapp.Controller;


import com.example.bankmanapp.Dto.ContoDto;
import com.example.bankmanapp.Service.ContoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/Conto")
public class ContoController {
    @Autowired
    ContoService contoService;

    @GetMapping(value = "/{utenteId}")
    public List<ContoDto> getContiUtente (@PathVariable Long utenteId ){
        List<ContoDto> contiDiUtente = contoService.trovaContiDiUtente(utenteId);
        for  (ContoDto contoDto : contiDiUtente ) {
            System.out.println("Conto" + contoDto.saldo());
        }
        return contiDiUtente;

    }




}
