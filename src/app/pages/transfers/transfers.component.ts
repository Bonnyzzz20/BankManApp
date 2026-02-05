import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/button/button.component';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../shared/api.service';

@Component({
  selector: 'app-transfers',
  standalone: true,
  imports: [CommonModule, ButtonComponent, FormsModule],
  templateUrl: './transfers.component.html',
  styleUrl: './transfers.component.css'
})
export class TransfersComponent {
  private apiService = inject(ApiService);

  accounts$ = this.apiService.getAccounts();

  transferData = {
    fromAccountId: null as number | null,
    beneficiary: '',
    iban: '',
    amount: null,
    reason: ''
  };

  recentTransfers: any[] = []; // In real app, load from API

  errorMsg = '';
  successMsg = '';

  constructor() {
    // Load recent txs from history for demo
    this.apiService.getTransactions().subscribe(txs => {
      this.recentTransfers = txs.slice(0, 5).map(t => ({
        id: t.id,
        beneficiary: t.description, // Approximation
        date: t.date,
        amount: Math.abs(t.amount),
        status: 'completed'
      }));
    });
  }

  onsubmit() {
    this.errorMsg = '';
    this.successMsg = '';

    // VALIDATIONS
    if (!this.transferData.fromAccountId) {
      this.errorMsg = 'Seleziona un conto di addebito.';
      return;
    }

    if (!this.transferData.amount || this.transferData.amount < 0.01) {
      this.errorMsg = 'L\'importo deve essere almeno 0.01€';
      return;
    }

    if (!this.transferData.reason) {
      this.errorMsg = 'La causale è obbligatoria.';
      return;
    }

    if (!this.transferData.iban) {
      this.errorMsg = 'IBAN obbligatorio.';
      return;
    }

    // Check IBAN
    this.apiService.checkIbanExists(this.transferData.iban).subscribe(exists => {
      // In our "Simulated" mode, we allow external IBANs too if they look valid.
      // But verify strictly if we want to enforce internal transfers only? 
      // User requirement: "interagire tra i conti dei vari utenti".

      const isInternalFormat = this.transferData.iban.startsWith('IT') && this.transferData.iban.length >= 15; // Loose check

      if (!isInternalFormat) {
        this.errorMsg = 'Formato IBAN non valido (usa un IBAN IT...)';
        return;
      }

      // Execute Transfer
      this.apiService.transferToUser(
        this.transferData.fromAccountId!,
        this.transferData.iban,
        this.transferData.amount!,
        this.transferData.reason
      ).subscribe(success => {
        if (success) {
          this.successMsg = 'Bonifico inviato con successo!';
          this.transferData = {
            fromAccountId: this.transferData.fromAccountId,
            beneficiary: '',
            iban: '',
            amount: null,
            reason: ''
          };
          // Refresh history
          this.apiService.getTransactions().subscribe(txs => {
            this.recentTransfers = txs.slice(0, 5).map(t => ({
              id: t.id,
              beneficiary: t.description,
              date: t.date,
              amount: Math.abs(t.amount),
              status: 'completed'
            }));
          });
        } else {
          this.errorMsg = 'Errore nel bonifico (Fondi insufficienti o errore tecnico).';
        }
      });
    });
  }
}
