import { Injectable, inject, computed, signal } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay, map, tap } from 'rxjs/operators';
import { Account, Transaction, Card, Bill } from './models';
import { PersistenceService } from './persistence.service';
import { User } from '../auth/auth.service';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    private persistence = inject(PersistenceService);

    private accounts: Account[] = [];
    private transactions: Transaction[] = [];
    private cards: Card[] = [];
    private bills: Bill[] = [];

    // Current User State
    private currentUserId: string | null = null;

    constructor() {
        this.initData();
    }

    // Initialize data for the CURRENT logged in user
    public initData() {
        const user = this.persistence.loadSession<User>('hb_current_user');
        this.currentUserId = user ? user.email : null; // Use email as unique ID for simplicity

        if (this.currentUserId) {
            this.loadUserData(this.currentUserId);
        } else {
            this.clearLocalData();
        }
    }

    private clearLocalData() {
        this.accounts = [];
        this.transactions = [];
        this.cards = [];
        this.bills = [];
    }

    private loadUserData(userId: string) {
        const storedAccounts = this.persistence.load<Account[]>(`hb_accounts_${userId}`);
        const storedTransactions = this.persistence.load<Transaction[]>(`hb_transactions_${userId}`);
        const storedCards = this.persistence.load<Card[]>(`hb_cards_${userId}`);
        const storedBills = this.persistence.load<Bill[]>(`hb_bills_${userId}`);

        if (storedAccounts) {
            this.accounts = storedAccounts;
            this.transactions = storedTransactions || [];
            this.cards = storedCards || [];
            this.bills = storedBills || [];
        } else {
            // First time for this user? Seed empty or default?
            // "cards must be unique for each user with their name on it"
            const user = this.persistence.loadSession<User>('hb_current_user');
            this.seedDataForUser(user!);
            this.saveState();
        }
    }

    private seedDataForUser(user: User) {
        // Unique IBAN generation based on user name/random
        const suffix = Math.floor(Math.random() * 900) + 100;

        this.accounts = [
            { id: 1, type: 'checking', name: 'Conto Corrente Premium', iban: `IT89 A123 4567 ${suffix} 0001`, balance: 0.00, movements: 0 }
        ];
        this.transactions = [];
        this.cards = [
            {
                id: 1,
                type: 'debit',
                number: `**** **** **** ${Math.floor(Math.random() * 8999) + 1000}`,
                holder: user.name,
                expiry: '12/28',
                color: 'blue'
            }
        ];
        this.bills = [
            { id: 1, billerName: 'Enel Energia', amount: 85.50, dueDate: '2026-11-15', status: 'pending', description: 'Bolletta Luce' },
            { id: 2, billerName: 'TIM', amount: 29.90, dueDate: '2026-11-10', status: 'pending', description: 'Internet Casa' }
        ];

        // Register in Global Directory
        this.registerInGlobalDirectory(this.accounts[0].iban, user.email, user.name);
    }

    private saveState() {
        if (!this.currentUserId) return;
        this.persistence.save(`hb_accounts_${this.currentUserId}`, this.accounts);
        this.persistence.save(`hb_transactions_${this.currentUserId}`, this.transactions);
        this.persistence.save(`hb_cards_${this.currentUserId}`, this.cards);
        this.persistence.save(`hb_bills_${this.currentUserId}`, this.bills);
    }

    // --- Global Directory (Simulated Backend) ---
    private registerInGlobalDirectory(iban: string, userId: string, name: string) {
        let dir = this.persistence.load<any[]>('hb_global_directory') || [];
        if (!dir.find(e => e.iban === iban)) {
            dir.push({ iban, userId, name });
            this.persistence.save('hb_global_directory', dir);
        }
    }

    // --- Public API ---

    // Accounts
    getAccounts(): Observable<Account[]> {
        // Refresh data ensures we are in sync if switched users
        this.initData();
        return of([...this.accounts]).pipe(delay(300));
    }

    addAccount(account: Omit<Account, 'id' | 'movements'>): Observable<Account> {
        const newAccount: Account = {
            ...account,
            id: (this.accounts.length > 0 ? Math.max(...this.accounts.map(a => a.id)) : 0) + 1,
            movements: 0
        };
        this.accounts.push(newAccount);

        // Register new Account
        const user = this.persistence.loadSession<User>('hb_current_user');
        if (user) this.registerInGlobalDirectory(newAccount.iban, user.email, user.name);

        this.saveState();
        return of(newAccount).pipe(delay(500));
    }

    deleteAccount(accountId: number): Observable<boolean> {
        this.accounts = this.accounts.filter(a => a.id !== accountId);
        // Also remove transactions/cards linked? Keeping simple for now.
        this.saveState();
        return of(true).pipe(delay(300));
    }

    // Transactions
    getTransactions(accountId?: number, month?: string): Observable<Transaction[]> {
        let txs = this.transactions;
        if (accountId) {
            txs = txs.filter(t => t.accountId === accountId);
        }
        // Month filter format 'YYYY-MM'
        if (month) {
            txs = txs.filter(t => t.date.startsWith(month));
        }
        // sort desc
        txs = txs.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
        return of([...txs]).pipe(delay(300));
    }

    // Cards
    getCards(): Observable<Card[]> {
        this.initData();
        return of([...this.cards]).pipe(delay(300));
    }

    topUpCard(cardId: number, amount: number): Observable<boolean> {
        const card = this.cards.find(c => c.id === cardId);
        if (card) {
            // For prepaid or just simulate 'top up' on any card
            card.balance = (card.balance || 0) + amount;
            this.saveState();
            return of(true).pipe(delay(800));
        }
        return of(false).pipe(delay(800));
    }

    updateCard(updatedCard: Card): Observable<boolean> {
        const index = this.cards.findIndex(c => c.id === updatedCard.id);
        if (index !== -1) {
            this.cards[index] = updatedCard;
            this.saveState();
            return of(true).pipe(delay(300));
        }
        return of(false);
    }

    deleteCard(cardId: number): Observable<boolean> {
        this.cards = this.cards.filter(c => c.id !== cardId);
        this.saveState();
        return of(true).pipe(delay(300));
    }

    addCard(card: Omit<Card, 'id'>): Observable<Card> {
        const newCard: Card = {
            ...card,
            id: (this.cards.length > 0 ? Math.max(...this.cards.map(c => c.id)) : 0) + 1
        };
        this.cards.push(newCard);
        this.saveState();
        return of(newCard).pipe(delay(500));
    }

    requestNewCard(type: 'debit' | 'credit' | 'prepaid', color: string): Observable<Card> {
        const user = this.persistence.loadSession<User>('hb_current_user');
        const number = `**** **** **** ${Math.floor(Math.random() * 8999) + 1000}`;
        const cvc = `${Math.floor(Math.random() * 899) + 100}`;

        return this.addCard({
            type,
            number,
            holder: user ? user.name : 'Unknown',
            expiry: '12/29',
            balance: type === 'prepaid' ? 0 : undefined,
            limit: type === 'credit' ? 3000 : undefined,
            color: color,
            cvc: cvc,
            isLocked: false
        });
    }

    // Bills
    getBills(): Observable<Bill[]> {
        return of([...this.bills]).pipe(delay(400));
    }

    payBill(billId: number, accountId: number): Observable<boolean> {
        const bill = this.bills.find(b => b.id === billId);
        const account = this.accounts.find(a => a.id === accountId);

        if (bill && account && bill.status === 'pending') {
            if (account.balance >= bill.amount) {
                account.balance -= bill.amount;
                bill.status = 'paid';

                // Record Transaction
                this.transactions.unshift({
                    id: Date.now(),
                    accountId: account.id,
                    amount: -bill.amount,
                    date: new Date().toISOString().split('T')[0],
                    description: `Pagamento Bollettino: ${bill.billerName}`,
                    type: 'expense'
                });

                this.saveState();
                return of(true).pipe(delay(600));
            }
        }
        return of(false).pipe(delay(600));
    }

    // Helpers
    checkIbanExists(iban: string): Observable<boolean> {
        // Check current user
        const existsInUser = this.accounts.some(a => a.iban === iban);
        // Check Global Directory
        const dir = this.persistence.load<any[]>('hb_global_directory') || [];
        const existsGlobal = dir.some(e => e.iban === iban);

        return of(existsInUser || existsGlobal).pipe(delay(300));
    }

    // Inter-User Transfer
    transferToUser(fromAccountId: number, toIban: string, amount: number, description: string): Observable<boolean> {
        const fromAccount = this.accounts.find(a => a.id === fromAccountId);
        if (!fromAccount || fromAccount.balance < amount) return of(false);

        // Deduct from Sender
        fromAccount.balance -= amount;
        fromAccount.movements++;
        this.transactions.push({
            id: Date.now(),
            accountId: fromAccountId,
            description: `Bonifico a ${toIban}: ${description}`,
            amount: -amount,
            date: new Date().toISOString().split('T')[0],
            type: 'expense'
        });
        this.saveState();

        // Find Receiver
        const dir = this.persistence.load<any[]>('hb_global_directory') || [];
        const entry = dir.find(e => e.iban === toIban);

        if (entry) {
            if (entry.userId === this.currentUserId) {
                // Self transfer (already handled by deducting? No, need to add to other account)
                const toAccount = this.accounts.find(a => a.iban === toIban);
                if (toAccount) {
                    toAccount.balance += amount;
                    toAccount.movements++;
                    this.transactions.push({
                        id: Date.now() + 1,
                        accountId: toAccount.id,
                        description: `Bonifico da ${fromAccount.iban}: ${description}`,
                        amount: amount,
                        date: new Date().toISOString().split('T')[0],
                        type: 'income'
                    });
                    this.saveState();
                    return of(true).pipe(delay(500));
                }
            } else {
                // Cross-User Transfer
                // Load Recipient Data separate from current user
                const recipientAccounts = this.persistence.load<Account[]>(`hb_accounts_${entry.userId}`);
                const recipientTxs = this.persistence.load<Transaction[]>(`hb_transactions_${entry.userId}`) || [];

                if (recipientAccounts) {
                    const toAccount = recipientAccounts.find(a => a.iban === toIban);
                    if (toAccount) {
                        toAccount.balance += amount;
                        toAccount.movements++;
                        recipientTxs.push({
                            id: Date.now(),
                            accountId: toAccount.id, // Recipient's account ID (might clash if IDs are 1,2,3... but they are local to user so ok)
                            description: `Bonifico da ${this.persistence.loadSession<User>('hb_current_user')?.name || 'Utente'}: ${description}`,
                            amount: amount,
                            date: new Date().toISOString().split('T')[0],
                            type: 'income'
                        });

                        // Save Recipient
                        this.persistence.save(`hb_accounts_${entry.userId}`, recipientAccounts);
                        this.persistence.save(`hb_transactions_${entry.userId}`, recipientTxs);
                        return of(true).pipe(delay(500));
                    }
                }
            }
        } else {
            // External/Unknown IBAN - just deduct (logic says checkIbanExists, but maybe valid external?)
            // For now assume all valid IBANs go through if format correct
            return of(true).pipe(delay(500));
        }

        return of(true).pipe(delay(500));
    }
}
