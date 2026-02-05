import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../shared/api.service';
import { Card } from '../../shared/models';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cards.component.html',
  styleUrl: './cards.component.css'
})
export class CardsComponent implements OnInit {
  private apiService = inject(ApiService);
  private route = inject(ActivatedRoute);

  cards$: Observable<Card[]> | undefined;

  // State
  flippedCardId: number | null = null;
  visibleCvv: { [key: number]: boolean } = {};

  // Settings Modal State
  showSettingsModal = false;
  showTopUpModal = false;
  showRequestCardModal = false;

  selectedCardId: number | null = null;
  topUpAmount: number = 0;
  topUpSuccess = false;

  // New Card Form
  newCardType: 'debit' | 'credit' | 'prepaid' = 'debit';
  newCardColor: string = 'blue';

  ngOnInit() {
    this.refreshCards();

    this.route.queryParams.subscribe(params => {
      if (params['action'] === 'topup' || params['section'] === 'topup') {
        this.showTopUpModal = true;
      }
    });
  }

  refreshCards() {
    this.cards$ = this.apiService.getCards();
  }

  // Flip Logic
  flipCard(id: number) {
    if (this.flippedCardId === id) {
      this.flippedCardId = null;
    } else {
      this.flippedCardId = id;
    }
  }

  toggleCvv(id: number, event: Event) {
    event.stopPropagation(); // Prevent card flip
    this.visibleCvv[id] = !this.visibleCvv[id];
  }

  // Lock Logic
  toggleLock(card: Card) {
    const action = card.isLocked ? 'sbloccare' : 'bloccare';
    const password = prompt(`Inserisci la password per ${action} la carta (Es: 1234):`);

    if (password) {
      const updated = { ...card, isLocked: !card.isLocked };
      this.apiService.updateCard(updated).subscribe(() => this.refreshCards());
    }
  }

  // Settings Logic
  openSettings(cardId: number) {
    this.selectedCardId = cardId;
    this.showSettingsModal = true;
  }

  updateCardColor(color: string) {
    if (!this.selectedCardId) return;

    // We need to fetch the card first to get full object, or just patch it?
    // ApiService updateCard expects simple card object. 
    // Let's get the card from the observable subscription? 
    // For simplicity, I'll fetch current list and find it.
    this.cards$?.subscribe(cards => {
      const card = cards.find(c => c.id === this.selectedCardId);
      if (card) {
        const updated = { ...card, color: color };
        this.apiService.updateCard(updated).subscribe(() => {
          this.refreshCards();
          this.showSettingsModal = false;
        });
      }
    });
  }

  // Delete Logic
  deleteCard(card: Card) {
    const password = prompt("Inserisci la password per CONFERMARE l'eliminazione:");
    if (password) {
      if (confirm('Sicuro di voler eliminare definitivamente questa carta?')) {
        this.apiService.deleteCard(card.id).subscribe(() => this.refreshCards());
      }
    }
  }

  // Top Up
  openTopUp(cardId?: number) {
    this.selectedCardId = cardId || null;
    this.showTopUpModal = true;
    this.topUpSuccess = false;
    this.topUpAmount = 0;
  }

  closeTopUp() {
    this.showTopUpModal = false;
    this.selectedCardId = null;
  }

  confirmTopUp() {
    if (this.selectedCardId && this.topUpAmount > 0) {
      this.apiService.topUpCard(this.selectedCardId, this.topUpAmount).subscribe(success => {
        if (success) {
          this.topUpSuccess = true;
          // Refresh immediately then wait to close
          this.refreshCards();
          setTimeout(() => {
            this.closeTopUp();
          }, 1500);
        } else {
          alert('Errore ricarica.');
        }
      });
    }
  }

  // Request New Card
  openRequestCard() {
    this.showRequestCardModal = true;
  }

  confirmRequestCard() {
    this.apiService.requestNewCard(this.newCardType, this.newCardColor).subscribe(() => {
      this.showRequestCardModal = false;
      this.refreshCards();
    });
  }
}