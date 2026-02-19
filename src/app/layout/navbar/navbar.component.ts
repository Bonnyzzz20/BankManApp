import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { ButtonComponent } from '../../shared/button/button.component';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  authService = inject(AuthService);
  isMenuOpen = false;

  navLinks = [
    { path: '/', label: 'Home' },
    { path: '/accounts', label: 'Conti' },
    { path: '/cards', label: 'Carte' },
    { path: '/transfers', label: 'Bonifici' }
  ];

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  closeMenu() {
    this.isMenuOpen = false;
  }

  handleLogout() {
    this.authService.logout();
    this.closeMenu();
  }
}
 