package com.example.bankmanapp.Repository;

import com.example.bankmanapp.Model.Movimenti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoRepository extends JpaRepository<Movimenti, Integer> {

}
