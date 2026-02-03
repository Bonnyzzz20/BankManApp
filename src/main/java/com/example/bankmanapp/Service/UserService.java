package  com.example.bankmanapp.Service;

import  com.example.bankmanapp.Dto.UserDto;
import  com.example.bankmanapp.Model.User;
import  com.example.bankmanapp.Repository.UserRepository;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

import  java.util.List;
import  java.util.stream.Collectors;

@Service
public  class  UserService  {

    @Autowired
    private  UserRepository  userRepository;

    //  Salva  un  nuovo  utente  e  restituisce  il  DTO  (senza  password)
    public  UserDto  registraUtente(User  nuovoUtente)  {
        User  utenteSalvato  =  userRepository.save(nuovoUtente);
        return  convertToDto(utenteSalvato);
    }

    //  Trova  un  utente  per  ID  e  restituisce  il  DTO
    public  UserDto  trovaPerId(Long  id)  {

        if (id == null || id <=0 )  {
            throw new IllegalArgumentException("ID non valido");
        }

        User  utente  =  userRepository.findById(id)
                .orElseThrow(()  ->  new  RuntimeException("Utente  non  trovato  con  ID:  "  +  id));

        return  convertToDto(utente);
    }


    //  Restituisce  tutti  gli  utenti  come  DTO
    public  List<UserDto>  findAll()  {
        return  userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //  Conversione  Model  ->  DTO
    private  UserDto  convertToDto(User  user)  {
        return  new  UserDto(
                user.getId(),
                user.getNome(),
                user.getEmail()
        );
    }
}
