export interface Account {
    id: number;
    name: string;
    iban: string;
    balance: number;
    type: 'checking' | 'savings' | 'investment' | 'credit';
    movements: number;
}

export interface Transaction {
    id: number;
    accountId: number;
    description: string;
    date: string;
    amount: number;
    type: 'income' | 'expense';
}

export interface Card {
    id: number;
    type: 'debit' | 'credit' | 'prepaid';
    number: string; // Masked or full
    holder: string;
    expiry: string;

    // Enhanced fields
    cvc?: string;
    isLocked?: boolean;
    color?: string; // e.g., 'gold', 'black', 'blue' or hex

    balance?: number; // For prepaid
    limit?: number; // For credit
}

export interface Bill {
    id: number;
    billerName: string; // e.g., "Enel Energia"
    amount: number;
    dueDate: string;
    status: 'pending' | 'paid';
    description: string;
}
