import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-button', // Nome del tag per usare questo componente: <app-button>
  standalone: true, // Componente moderno che non richiede moduli
  imports: [CommonModule], // Importiamo moduli comuni se servono
  templateUrl: './button.component.html',
  styleUrl: './button.component.css'
})
export class ButtonComponent {
  // Input: Proprietà che possono essere passate dall'esterno
  @Input() label: string = 'Button'; // Il testo da mostrare nel bottone
  @Input() type: 'button' | 'submit' | 'reset' = 'button'; // Tipo di bottone HTML
  @Input() variant: 'primary' | 'secondary' | 'outline' = 'primary'; // Stile visivo: primario (verde), secondario (lime), outline (solo bordo)
  @Input() disabled: boolean = false; // Se è vero, il bottone è disabilitato
  
  // Output: Eventi che questo componente può inviare al padre
  @Output() onClick = new EventEmitter<Event>(); // Emette un evento quando viene cliccato

  // Gestisce il click sul bottone
  handleClick(event: Event) {
    if (!this.disabled) {
      this.onClick.emit(event); // Manda l'evento solo se non è disabilitato
    }
  }
}
