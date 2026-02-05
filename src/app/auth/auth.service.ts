import { Injectable, signal, computed, inject } from '@angular/core';
import { Router } from '@angular/router';
import { PersistenceService } from '../shared/persistence.service';

export interface User {
  name: string;
  email: string;
  password?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private persistence = inject(PersistenceService);
  private router = inject(Router);

  // CURRENT SESSION: Stored in SessionStorage so it clears on browser close/restart
  currentUser = signal<User | null>(this.persistence.loadSession<User>('hb_current_user'));

  isAuthenticated = computed(() => !!this.currentUser());

  constructor() { }

  register(name: string, email: string, password?: string): boolean {
    // 1. Load existing users "DB"
    let users = this.persistence.load<User[]>('hb_users') || [];

    // 2. Check duplicates
    if (users.some(u => u.email === email)) {
      alert('Utente già registrato con questa email.');
      return false;
    }

    const newUser: User = { name, email, password };
    users.push(newUser);

    // 3. Save to "DB"
    this.persistence.save('hb_users', users);

    // 4. Auto-login (save to session)
    this.persistence.saveSession('hb_current_user', newUser);
    this.currentUser.set(newUser);
    this.router.navigate(['/']);
    return true;
  }

  login(email: string, password?: string): boolean {
    // 1. Load users "DB"
    const users = this.persistence.load<User[]>('hb_users') || [];

    // 2. Find user
    const foundUser = users.find(u => u.email === email && u.password === password);

    if (foundUser) {
      // 3. Save to session
      this.persistence.saveSession('hb_current_user', foundUser);
      this.currentUser.set(foundUser);
      this.router.navigate(['/']);
      return true;
    } else {
      alert('Credenziali non valide.');
      return false;
    }
  }

  logout() {
    this.persistence.clearSession('hb_current_user');
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }
}

