package com.example.bankmanapp.Repository;

import com.example.bankmanapp.Model.Carta;
import com.example.bankmanapp.Model.Movimenti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaRepository extends JpaRepository<Carta, Integer> {
    // List<Movimenti> findByIdConto(Long idConto);

}
