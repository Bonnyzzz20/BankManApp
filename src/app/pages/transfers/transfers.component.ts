import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/button/button.component';

@Component({
  selector: 'app-transfers',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  template: `
    <div class="page-container">
      <h1>Effettua un Bonifico</h1>
      <p>Trasferisci denaro in modo semplice e veloce.</p>
      <div class="transfer-form">
        <p>Modulo bonifico in costruzione...</p>
        <app-button label="Esegui Bonifico Demo" variant="primary"></app-button>
      </div>
    </div>
  `,
  styles: [`
    .page-container { padding: 2rem; max-width: 1200px; margin: 0 auto; }
    h1 { color: var(--primary-color); margin-bottom: 1rem; }
    .transfer-form { background: white; padding: 2rem; border-radius: 8px; box-shadow: var(--shadow-sm); }
  `]
})
export class TransfersComponent {}
