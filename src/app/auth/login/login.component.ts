import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { ButtonComponent } from '../../shared/button/button.component';

import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ButtonComponent, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginData = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService) {}

  handleLogin() {
    if (this.loginData.email && this.loginData.password) {
      const success = this.authService.login(this.loginData.email, this.loginData.password);
      if (!success) {
        alert('Login fallito: credenziali errate');
      }
    } else {
        alert('Inserisci email e password');
    }
  }
}
