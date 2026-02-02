import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from './auth.service';

// Guard funzionale (moderno Angular) per proteggere le rotte
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true; // Utente loggato, accesso consentito
  } else {
    // Utente non loggato, reindirizza al login
    return router.parseUrl('/login');
  }
};
