import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../shared/api.service';
import { Account, Transaction, Bill } from '../../shared/models';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit {
  private apiService = inject(ApiService);
  private route = inject(ActivatedRoute);

  accounts$: Observable<Account[]> | undefined;
  transactions$: Observable<Transaction[]> | undefined;
  bills$: Observable<Bill[]> | undefined;

  activeSection: 'accounts' | 'history' | 'bills' = 'accounts';

  selectedAccount: Account | null = null;
  selectedBill: Bill | null = null;

  // Modals
  showAddAccountModal = false;
  showPayBillModal = false;

  // New Account Form
  newAccountName = '';
  newAccountType: 'checking' | 'savings' = 'checking';
  newAccountIban = '';

  // Filter
  selectedMonth: string = new Date().toISOString().slice(0, 7); // YYYY-MM

  ngOnInit() {
    this.refreshData();

    this.route.queryParams.subscribe(params => {
      if (params['section']) {
        this.activeSection = params['section'];
      }
    });
  }

  refreshData() {
    this.accounts$ = this.apiService.getAccounts();
    this.transactions$ = this.apiService.getTransactions(undefined, this.selectedMonth);
    this.bills$ = this.apiService.getBills();
  }

  setSection(section: 'accounts' | 'history' | 'bills') {
    this.activeSection = section;
    this.selectedAccount = null;
  }

  openAccountDetails(account: Account) {
    this.selectedAccount = account;
    this.activeSection = 'history'; // Reuse history view but filtered
    // Update transactions to show only this account
    this.transactions$ = this.apiService.getTransactions(account.id, this.selectedMonth);
  }

  onMonthChange(event: any) {
    this.selectedMonth = event.target.value;
    if (this.selectedAccount) {
      this.transactions$ = this.apiService.getTransactions(this.selectedAccount.id, this.selectedMonth);
    } else {
      this.transactions$ = this.apiService.getTransactions(undefined, this.selectedMonth);
    }
  }

  // --- Add Account ---
  openAddAccount() {
    this.showAddAccountModal = true;
    this.newAccountIban = 'IT' + Math.floor(Math.random() * 100); // Pre-fill country
  }

  createAccount() {
    if (!this.newAccountName || !this.newAccountIban) return;

    // Simple Validation
    if (!this.newAccountIban.startsWith('IT') || this.newAccountIban.length < 15) {
      alert('IBAN non valido (Deve iniziare con IT e avere almeno 15 caratteri)');
      return;
    }

    this.apiService.checkIbanExists(this.newAccountIban).subscribe(exists => {
      if (exists) {
        alert('Questo IBAN esiste già!');
      } else {
        this.apiService.addAccount({
          name: this.newAccountName,
          type: this.newAccountType,
          iban: this.newAccountIban,
          balance: 0
        }).subscribe(() => {
          this.showAddAccountModal = false;
          this.refreshData();
        });
      }
    });
  }

  deleteAccount(id: number) {
    if (confirm('Sei sicuro di voler eliminare questo conto?')) {
      this.apiService.deleteAccount(id).subscribe(() => this.refreshData());
    }
  }

  // --- Bill Payment ---
  openPayBill(bill: Bill) {
    this.selectedBill = bill;
    this.showPayBillModal = true;
  }

  payBill(accountId: number) {
    if (!this.selectedBill) return;

    this.apiService.payBill(this.selectedBill.id, accountId).subscribe(success => {
      if (success) {
        alert('Pagamento effettuato!');
        this.showPayBillModal = false;
        this.selectedBill = null;
        this.refreshData();
      } else {
        alert('Fondi insufficienti o errore.');
      }
    });
  }

  copyIban(iban: string) {
    navigator.clipboard.writeText(iban).then(() => {
      alert('IBAN copiato negli appunti!');
    });
  }
}

