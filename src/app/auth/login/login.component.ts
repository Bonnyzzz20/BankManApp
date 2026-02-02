import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { ButtonComponent } from '../../shared/button/button.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: '../register/register.component.css' // Usiamo lo stesso CSS della registrazione
})
export class LoginComponent {
  constructor(private authService: AuthService) {}

  handleLogin(email: string, password?: string) {
    if (email && password) {
      const success = this.authService.login(email, password);
      if (!success) {
        alert('Login fallito: credenziali errate');
      }
    } else {
        alert('Inserisci email e password');
    }
  }
}
