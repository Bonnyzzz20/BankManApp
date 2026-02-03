package com.example.bankmanapp.Controller;


import com.example.bankmanapp.Dto.UserDto;
import com.example.bankmanapp.Model.User;
import com.example.bankmanapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/User")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable Long id) {
        UserDto user = userService.trovaPerId(id);
        //System.out.println("user:"+ user.nome());
        return user;
    }




}
















//studiarti responseentity di tipo userdto( quello che ti serve in quel controller)
// creare un dto per getsire il register dell'utente (doppia password, doppia mail ,codice ficale.....)
//inseririlo nell firm del metodo di register
//modificare il service e la repos con delle logiche per gestire la registrazione dell'utente
// implementare i metodi crud in questo controller
//configurare postman web

//