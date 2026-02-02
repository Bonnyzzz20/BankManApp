import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AccountsComponent } from './pages/accounts/accounts.component';
import { CardsComponent } from './pages/cards/cards.component';
import { TransfersComponent } from './pages/transfers/transfers.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { authGuard } from './auth/auth.guard';
import { guestGuard } from './auth/guest.guard';

export const routes: Routes = [
  //con '' ti porta alla home, mentre con '**' ti porta alla home se cerchi una pagina che non esiste
  { path: '', component: HomeComponent, canActivate: [authGuard] }, // Ora la home è protetta
  { path: 'accounts', component: AccountsComponent, canActivate: [authGuard] },
  { path: 'cards', component: CardsComponent, canActivate: [authGuard] },
  { path: 'transfers', component: TransfersComponent, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [guestGuard] },
  { path: 'login', component: LoginComponent, canActivate: [guestGuard] },
  { path: '**', redirectTo: '' }
];
