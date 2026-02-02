import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  private authService = inject(AuthService);
  currentUser = this.authService.currentUser;

  // Dati finti (mock) per simulare i conti dell'utente
  // In un'app reale questi arriverebbero da un servizio/API
  accounts = [
    { id: 1, type: 'checking', name: 'Conto Corrente Premium', iban: 'IT89 A123 4567 8901', balance: 12543.50 },
    { id: 2, type: 'credit', name: 'Carta di Credito Gold', iban: 'IT89 A987 6543 2109', balance: -450.00 },
    { id: 3, type: 'savings', name: 'Conto Risparmio', iban: 'IT89 A246 8135 7902', balance: 50000.00 }
  ];

  transactions = [
    { id: 1, description: 'Amazon IT', date: '2023-10-25', amount: -89.90, type: 'expense' },
    { id: 2, description: 'Stipendio', date: '2023-10-27', amount: 2450.00, type: 'income' },
    { id: 3, description: 'Netflix', date: '2023-10-28', amount: -12.99, type: 'expense' },
    { id: 4, description: 'Ristorante Da Mario', date: '2023-10-29', amount: -65.00, type: 'expense' }
  ];

  // Funzione semplice per gestire i click sui bottoni di azione rapida
  handleQuickAction(action: string) {
    console.log(`Azione attivata: ${action}`);
    // Qui andrebbe la logica per navigare o aprire un modale
  }
}
