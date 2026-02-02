import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent {
  accounts = [
    { 
      id: 1, 
      name: 'Conto Corrente Premium', 
      iban: 'IT89 A123 4567 8901', 
      balance: 12543.50,
      type: 'checking',
      movements: 12
    },
    { 
      id: 2, 
      name: 'Conto Risparmio', 
      iban: 'IT89 A246 8135 7902', 
      balance: 50000.00,
      type: 'savings',
      movements: 2
    },
    { 
      id: 3, 
      name: 'Conto Investimenti', 
      iban: 'IT12 B987 6543 2109', 
      balance: 15400.20,
      type: 'investment',
      movements: 5
    }
  ];
}
