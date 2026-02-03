package com.example.bankmanapp.Repository;

import com.example.bankmanapp.Model.Conto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContoRepository extends JpaRepository<Conto, Long> {

    List<Conto> findByIdUtente(Long idUtente);
}