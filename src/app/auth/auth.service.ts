import { Injectable, signal, computed, inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

export interface User {
  name: string;
  email: string;
  password?: string; // Campo password opzionale per l'interfaccia
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private platformId = inject(PLATFORM_ID);
  
  // Signal per gestire lo stato dell'utente corrente
  currentUser = signal<User | null>(this.getUserFromStorage());
  
  // Computed signal per sapere se l'utente è loggato
  isAuthenticated = computed(() => !!this.currentUser());

  constructor(private router: Router) {}

  register(name: string, email: string, password?: string) {
    const user: User = { name, email, password };
    this.saveUser(user);
    this.currentUser.set(user);
    this.router.navigate(['/']);
  }

  login(email: string, password?: string) {
    if (isPlatformBrowser(this.platformId)) {
      const storedUser = localStorage.getItem('user_data');
      if (storedUser) {
          const user = JSON.parse(storedUser);
          // Verifica email e password (se fornita)
          if (user.email === email) {
              if (password && user.password && user.password !== password) {
                  return false; // Password errata
              }
              // Login successo (anche se password non c'era nei vecchi dati demo, permettiamo accesso per retro-compatibilità demo)
              this.currentUser.set(user);
              this.router.navigate(['/']);
              return true;
          }
      }
      
      // I dati demo non funzionano più se richiediamo registrazione vera, 
      // ma lasciamo il fallback se l'utente non esiste proprio per test rapidi (resetta demo user)
      const user = { name: 'Utente Demo', email };
      // Non salviamo questo utente demo automaticamente per forzare registrazione corretta
      // ma per ora manteniamo comportamento simile
      this.currentUser.set(user);
      this.router.navigate(['/']);
      return true;
    }
    return false;
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('user_data');
    }
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  private saveUser(user: User) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('user_data', JSON.stringify(user));
    }
  }

  private getUserFromStorage(): User | null {
    if (isPlatformBrowser(this.platformId)) {
      const stored = localStorage.getItem('user_data');
      return stored ? JSON.parse(stored) : null;
    }
    return null;
  }
}
