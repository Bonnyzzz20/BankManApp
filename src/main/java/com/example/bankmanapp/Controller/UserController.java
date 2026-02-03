package com.example.bankmanapp.Controller;


import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/User")
public class UserController {
    @Autowired
    UserService userService;

    //metodo che trova un solo utente tramite id
    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable Long id) {
        UserDto user = userService.trovaPerId(id);
        return user;
    }

    //metodo che restituisce tutti i record in db
    @GetMapping(value= "/Users")
    public List<UserDto>  getAllUsers(){
     return userService.findAll();
    }


}
















//studiarti responseentity di tipo userdto( quello che ti serve in quel controller)
// creare un dto per getsire il register dell'utente (doppia password, doppia mail ,codice ficale.....)
//inseririlo nell firm del metodo di register
//modificare il service e la repos con delle logiche per gestire la registrazione dell'utente
// implementare i metodi crud in questo controller
//configurare postman web

//