import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { ButtonComponent } from '../../shared/button/button.component';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, ButtonComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  authService = inject(AuthService); // Inject del servizio auth

  navLinks = [
    { path: '/', label: 'Home' },
    { path: '/accounts', label: 'Conti' },
    { path: '/cards', label: 'Carte' },
    { path: '/transfers', label: 'Bonifici' }
  ];

  handleLogout() {
    this.authService.logout();
  }
}
