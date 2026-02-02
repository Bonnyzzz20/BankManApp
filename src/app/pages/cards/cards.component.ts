import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="page-container">
      <h1>Le tue Carte</h1>
      <p>Monitora e gestisci le tue carte di pagamento.</p>
      <div class="placeholder-content">
        <p>Funzionalità in arrivo...</p>
      </div>
    </div>
  `,
  styles: [`
    .page-container { padding: 2rem; max-width: 1200px; margin: 0 auto; }
    h1 { color: var(--primary-color); margin-bottom: 1rem; }
    .placeholder-content { padding: 2rem; background: #fff; border-radius: 8px; text-align: center; color: #888; }
  `]
})
export class CardsComponent {}
