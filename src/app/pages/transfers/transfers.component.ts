import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/button/button.component';

import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transfers',
  standalone: true,
  imports: [CommonModule, ButtonComponent, FormsModule],
  templateUrl: './transfers.component.html',
  styleUrl: './transfers.component.css'
})
export class TransfersComponent {
  transferData = {
    beneficiary: '',
    iban: '',
    amount: null,
    reason: ''
  };

  recentTransfers = [
    { id: 1, beneficiary: 'Mario Rossi', date: '2023-11-01', amount: 150.00, status: 'completed' },
    { id: 2, beneficiary: 'Enel Energia', date: '2023-10-28', amount: 84.50, status: 'completed' },
    { id: 3, beneficiary: 'Condominio', date: '2023-10-15', amount: 200.00, status: 'pending' },
    { id: 4, beneficiary: 'Luigi Verdi', date: '2023-10-10', amount: 50.00, status: 'completed' }
  ];

  onsubmit() {
    console.log('Bonifico inviato:', this.transferData);
    alert('Funzionalità demo: Dati bonifico stampati in console!');
  }
}
