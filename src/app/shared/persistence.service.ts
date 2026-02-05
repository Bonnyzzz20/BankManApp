import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
    providedIn: 'root'
})
export class PersistenceService {

    constructor(@Inject(PLATFORM_ID) private platformId: Object) { }

    // Generic Save
    save(key: string, data: any): void {
        if (isPlatformBrowser(this.platformId)) {
            localStorage.setItem(key, JSON.stringify(data));
        }
    }

    // Generic Load
    load<T>(key: string): T | null {
        if (isPlatformBrowser(this.platformId)) {
            const stored = localStorage.getItem(key);
            if (stored) {
                try {
                    return JSON.parse(stored) as T;
                } catch (e) {
                    console.error('Error parsing stored data', e);
                    return null;
                }
            }
        }
        return null;
    }

    // Clear
    clear(key: string): void {
        if (isPlatformBrowser(this.platformId)) {
            localStorage.removeItem(key);
        }
    }

    // Specific Session Management (SessionStorage) - For "Login state" that clears on close if needed
    // However, requirements say "cancel cache and don't remain logged in on restart". 
    // This implies sessionStorage is better for the *current user token/state*, 
    // while localStorage is for the "DB" (users list, accounts, etc).

    saveSession(key: string, data: any): void {
        if (isPlatformBrowser(this.platformId)) {
            sessionStorage.setItem(key, JSON.stringify(data));
        }
    }

    loadSession<T>(key: string): T | null {
        if (isPlatformBrowser(this.platformId)) {
            const stored = sessionStorage.getItem(key);
            return stored ? JSON.parse(stored) as T : null;
        }
        return null;
    }

    clearSession(key: string): void {
        if (isPlatformBrowser(this.platformId)) {
            sessionStorage.removeItem(key);
        }
    }
}
