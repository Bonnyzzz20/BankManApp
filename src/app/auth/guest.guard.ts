import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from './auth.service';

// Guard per impedire agli utenti loggati di accedere a login/registrazione
export const guestGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    // Utente già loggato, reindirizza alla home
    return router.parseUrl('/');
  } else {
    // Utente non loggato, accesso consentito
    return true;
  }
};
