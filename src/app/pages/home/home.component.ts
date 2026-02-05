import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth/auth.service';
import { ApiService } from '../../shared/api.service';
import { Account, Transaction } from '../../shared/models';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private authService = inject(AuthService);
  private apiService = inject(ApiService);
  private router = inject(Router);

  currentUser = this.authService.currentUser;

  accounts$: Observable<Account[]> | undefined;
  transactions$: Observable<Transaction[]> | undefined;

  // New Account Form State
  showAddAccountForm = false;
  newAccountName = '';
  newAccountIban = '';
  newAccountType: 'checking' | 'savings' | 'investment' = 'checking';

  ngOnInit() {
    this.refreshData();
  }

  refreshData() {
    this.accounts$ = this.apiService.getAccounts();
    this.transactions$ = this.apiService.getTransactions();
  }

  handleQuickAction(action: string) {
    switch (action) {
      case 'transfer':
        this.router.navigate(['/transfers']);
        break;
      case 'topup':
        // Assuming we implement a specific route for this, or general cards
        this.router.navigate(['/cards']);
        break;
      case 'bills':
        this.router.navigate(['/accounts'], { queryParams: { section: 'bills' } });
        break;
      case 'cards':
        this.router.navigate(['/cards']);
        break;
      default:
        console.log(`Unknown action: ${action}`);
    }
  }

  toggleAddAccount() {
    this.showAddAccountForm = !this.showAddAccountForm;
  }

  submitNewAccount() {
    if (this.newAccountName && this.newAccountIban) {
      this.apiService.addAccount({
        name: this.newAccountName,
        iban: this.newAccountIban,
        type: this.newAccountType as any,
        balance: 0 // Start with 0
      }).subscribe(() => {
        this.showAddAccountForm = false;
        this.newAccountName = '';
        this.newAccountIban = '';
        this.refreshData(); // Reload list
      });
    }
  }
}

