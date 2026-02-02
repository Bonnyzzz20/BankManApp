import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/button/button.component'; // Importiamo il nostro componente bottone condiviso

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ButtonComponent], // Dobbiamo dichiarare che usiamo ButtonComponent qui
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  // Dati finti (mock) per simulare i conti dell'utente
  // In un'app reale questi arriverebbero da un servizio/API
  accounts = [
    { id: 1, name: 'Conto Corrente Premium', iban: 'IT89 A123 4567 8901', balance: 12543.50 },
    { id: 2, name: 'Carta di Credito Gold', iban: 'IT89 A987 6543 2109', balance: -450.00 },
    { id: 3, name: 'Conto Risparmio', iban: 'IT89 A246 8135 7902', balance: 50000.00 }
  ];

  // Funzione semplice per gestire i click sui bottoni di azione rapida
  handleQuickAction(action: string) {
    console.log(`Azione attivata: ${action}`);
    // Qui andrebbe la logica per navigare o aprire un modale
  }
}
