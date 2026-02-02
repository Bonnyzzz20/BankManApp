import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { ButtonComponent } from '../../shared/button/button.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private authService: AuthService) {}

  handleRegister(name: string, email: string, password?: string) {
    if (name && email && password) {
      this.authService.register(name, email, password);
    } else {
        alert('Compila tutti i campi, inclusa la password!');
    }
  }
}
