import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { ButtonComponent } from '../../shared/button/button.component';

import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ButtonComponent, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerData = {
    name: '',
    email: '',
    password: ''
  };

  constructor(private authService: AuthService) {}

  handleRegister() {
    if (this.registerData.name && this.registerData.email && this.registerData.password) {
      this.authService.register(this.registerData.name, this.registerData.email, this.registerData.password);
    } else {
        alert('Compila tutti i campi, inclusa la password!');
    }
  }
}
