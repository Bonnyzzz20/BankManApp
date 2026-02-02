import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cards.component.html',
  styleUrl: './cards.component.css'
})
export class CardsComponent {
  cards = [
    {
      id: 1,
      number: '4532 1234 5678 9012',
      holder: 'BIAGIO SCAGLIA',
      expiry: '12/28',
      cvc: '123',
      type: 'visa',
      variant: 'gold',
      balance: 1540.50
    },
    {
      id: 2,
      number: '5412 7512 3412 3456',
      holder: 'BIAGIO SCAGLIA',
      expiry: '09/26',
      cvc: '456',
      type: 'mastercard',
      variant: 'black',
      balance: -450.00
    },
    {
      id: 3,
      number: '4532 0000 1111 2222',
      holder: 'BIAGIO SCAGLIA',
      expiry: '05/29',
      cvc: '789',
      type: 'visa',
      variant: 'blue',
      balance: 25000.00
    }
  ];
}
