package com.example.bankmanapp.Repository;

import com.example.bankmanapp.Model.Movimenti;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentoRepository extends JpaRepository<Movimenti, Long> {
    List<Movimenti> findByIdConto(Long idConto);

}
