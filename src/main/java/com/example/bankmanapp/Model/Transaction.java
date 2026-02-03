package com.example.bankmanapp.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private Long idConto;
    private BigDecimal saldoAlMomento;
    private List<Movimenti> movimenti;
    private LocalDateTime dataReport;

    public Transaction(Long idConto, BigDecimal saldoAlMomento, List<Movimenti> movimenti) {
        this.idConto = idConto;
        this.saldoAlMomento = saldoAlMomento;
        this.movimenti = movimenti;
        this.dataReport = LocalDateTime.now();
    }

    // Solo Getter
    public Long getIdConto() { return idConto; }
    public BigDecimal getSaldoAlMomento() { return saldoAlMomento; }
    public List<Movimenti> getMovimenti() { return movimenti; }
    public LocalDateTime getDataReport() { return dataReport; }
}
